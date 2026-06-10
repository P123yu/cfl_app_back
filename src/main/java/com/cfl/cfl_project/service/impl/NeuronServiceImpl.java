package com.cfl.cfl_project.service.impl;

import com.cfl.cfl_project.dto.NeuronResponse;
import com.cfl.cfl_project.service.NeuronService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.*;

@Service
public class NeuronServiceImpl implements NeuronService {

    private static final Logger log = LoggerFactory.getLogger(NeuronServiceImpl.class);

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    @Value("${gemini.api.url:https://api.groq.com/openai/v1/chat/completions}")
    private String geminiApiUrl;

    @Value("${gemini.model:qwen/qwen3-32b}")
    private String geminiModel;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private String cmsKnowledgeCache = null;

    private synchronized String getSystemContext() {
        if (cmsKnowledgeCache == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("You are CFL Neuron, a helpful AI assistant for the CMS Future Leaders (CFL) program run by CMS (Computer Management System) Computers (https://www.cms.co.in/).\n\n");
            try (java.io.InputStream is = getClass().getClassLoader().getResourceAsStream("cms_knowledge.txt")) {
                if (is != null) {
                    try (java.util.Scanner scanner = new java.util.Scanner(is, java.nio.charset.StandardCharsets.UTF_8.name())) {
                        sb.append(scanner.useDelimiter("\\A").next());
                    }
                } else {
                    sb.append("CMS is Computer Management System Computers (https://www.cms.co.in/). CFL is CMS Future Leaders.");
                }
            } catch (Exception e) {
                log.error("Failed to load cms_knowledge.txt", e);
                sb.append("CMS is Computer Management System Computers (https://www.cms.co.in/). CFL is CMS Future Leaders.");
            }
            cmsKnowledgeCache = sb.toString() + "\n\n";
        }
        return cmsKnowledgeCache;
    }

    @Override
    public NeuronResponse askQuestion(String question) {
        log.info("Received question: {}", question);
        if (question == null || question.trim().isEmpty()) {
            return new NeuronResponse("Please provide a question.", "");
        }

        // 1. Fetch DB schema metadata dynamically
        String schema = getDatabaseSchema();

        // 2. Ask LLM to generate SQL or decide if NO_SQL
        String sqlGenerationPrompt = String.format(
            getSystemContext() +
            "You are a database query assistant. Given the PostgreSQL database schema below and the user's natural language question:\n" +
            "1. Decide if the question can/should be answered by querying the database tables.\n" +
            "2. If yes, write a read-only PostgreSQL SELECT query to fetch the necessary data. Return ONLY the raw SQL query. Do not wrap in markdown or backticks or anything. No comments.\n" +
            "3. If no (e.g. it is a general question, greeting, or general advice/recommendation that does not require database records), return exactly 'NO_SQL'.\n\n" +
            "Database Schema:\n%s\n\n" +
            "User Question: %s\n\n" +
            "Response (SQL or NO_SQL):",
            schema, question
        );

        String llmSqlOutput = callGemini(sqlGenerationPrompt).trim();
        log.info("Gemini generated SQL output: {}", llmSqlOutput);

        // Remove markdown backticks if Gemini returned them anyway
        if (llmSqlOutput.startsWith("```")) {
            llmSqlOutput = llmSqlOutput.replaceAll("```sql|```", "").trim();
        }

        String checkSql = llmSqlOutput.toLowerCase().trim();
        boolean isQuery = checkSql.startsWith("select") || checkSql.startsWith("with");
        if (checkSql.contains("no_sql") || checkSql.isEmpty() || !isQuery) {
            // General query - no database fetch
            String generalPrompt = String.format(
                getSystemContext() +
                "Answer the following user question/request in a friendly and professional manner:\n\n%s",
                question
            );
            String generalAnswer = callGemini(generalPrompt);
            return new NeuronResponse(generalAnswer, "");
        }

        // Validate generated SQL query to ensure it's safe and read-only
        String sqlQuery = llmSqlOutput;
        if (!isSafeSelectQuery(sqlQuery)) {
            log.warn("Query blocked for safety: {}", sqlQuery);
            return new NeuronResponse("The assistant generated a query that was flagged as unsafe or invalid. Query: " + sqlQuery, sqlQuery);
        }

        // Execute SQL query
        List<Map<String, Object>> queryResults;
        try {
            queryResults = jdbcTemplate.queryForList(sqlQuery);
            log.info("Query returned {} rows", queryResults.size());
        } catch (Exception e) {
            log.error("Error executing query: " + sqlQuery, e);
            // Try to fallback to general LLM processing or let LLM explain the failure
            String errorPrompt = String.format(
                getSystemContext() +
                "We tried to answer the user's question by running a database query, but it failed with an error.\n" +
                "User Question: %s\n" +
                "Attempted SQL: %s\n" +
                "Error details: %s\n\n" +
                "Explain the issue to the user and attempt to answer the question generally if possible.",
                question, sqlQuery, e.getMessage()
            );
            String errResponse = callGemini(errorPrompt);
            return new NeuronResponse(errResponse, sqlQuery);
        }

        // Convert query results to string representation
        String resultsString;
        try {
            resultsString = objectMapper.writeValueAsString(sanitizeQueryResults(queryResults));
        } catch (Exception e) {
            resultsString = sanitizeQueryResults(queryResults).toString();
        }

        // Ask LLM to synthesize final natural language answer based on query results
        String answerPrompt = String.format(
            getSystemContext() +
            "Based on the database query results below, answer the user's question accurately.\n\n" +
            "User Question: %s\n" +
            "SQL Query Executed: %s\n" +
            "Query Results:\n%s\n\n" +
            "Answer the question clearly, in natural language, referencing the data above. If the data is empty, mention that no records were found.",
            question, sqlQuery, resultsString
        );

        String finalAnswer = callGemini(answerPrompt);
        return new NeuronResponse(finalAnswer, sqlQuery);
    }

    private String getDatabaseSchema() {
        StringBuilder schema = new StringBuilder();
        try (Connection conn = dataSource.getConnection()) {
            DatabaseMetaData metaData = conn.getMetaData();
            try (ResultSet tables = metaData.getTables(conn.getCatalog(), "public", "%", new String[]{"TABLE"})) {
                while (tables.next()) {
                    String tableName = tables.getString("TABLE_NAME");
                    if (tableName.startsWith("pg_") || tableName.equals("flyway_schema_history")) {
                        continue;
                    }
                    schema.append("- ").append(tableName).append(": ");
                    List<String> colDetails = new ArrayList<>();
                    try (ResultSet columns = metaData.getColumns(conn.getCatalog(), "public", tableName, "%")) {
                        while (columns.next()) {
                            String columnName = columns.getString("COLUMN_NAME");
                            String typeName = columns.getString("TYPE_NAME");
                            colDetails.add(columnName + " (" + typeName + ")");
                        }
                    }
                    schema.append(String.join(", ", colDetails)).append("\n");
                }
            }
        } catch (Exception e) {
            log.error("Error fetching db schema metadata", e);
            schema.append("Primary tables:\n")
                  .append("- cfl_table: id, emp_id, cfl_first_name, cfl_last_name, cfl_email, mentor_name, reporting_manager, cfl_designation, cfl_department, probation_status, year\n")
                  .append("- cfl_skill: id, emp_id, primary_skills, secondary_skills, other_skills, quarter, year\n")
                  .append("- goal_setting_tracker: id, cfl_id, cfl_name, quarter, year, goal_initiated_from_hr_to_manager\n");
        }
        return schema.toString();
    }

    private boolean isSafeSelectQuery(String sql) {
        String cleanSql = sql.trim().toLowerCase();
        if (!cleanSql.startsWith("select")) {
            return false;
        }
        // Check for disallowed mutating keywords
        String[] forbiddenKeywords = {
            "insert", "update", "delete", "drop", "truncate", "alter", "create", 
            "replace", "grant", "revoke", "pg_sleep", "execute", "copy", "merge"
        };
        for (String keyword : forbiddenKeywords) {
            if (cleanSql.contains(" " + keyword + " ") || cleanSql.contains(" " + keyword) || cleanSql.contains(keyword + " ")) {
                return false;
            }
        }
        return true;
    }

    private List<Map<String, Object>> sanitizeQueryResults(List<Map<String, Object>> results) {
        if (results == null) {
            return null;
        }
        List<Map<String, Object>> sanitizedList = new ArrayList<>();
        for (Map<String, Object> row : results) {
            Map<String, Object> sanitizedRow = new LinkedHashMap<>();
            for (Map.Entry<String, Object> entry : row.entrySet()) {
                Object value = entry.getValue();
                if (value instanceof byte[]) {
                    byte[] bytes = (byte[]) value;
                    sanitizedRow.put(entry.getKey(), "[Binary Data: " + bytes.length + " bytes]");
                } else if (value != null && value.getClass().isArray()) {
                    sanitizedRow.put(entry.getKey(), "[Array Data]");
                } else {
                    sanitizedRow.put(entry.getKey(), value);
                }
            }
            sanitizedList.add(sanitizedRow);
        }
        return sanitizedList;
    }

    private String callGemini(String prompt) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            Map<String, Object> message = new HashMap<>();
            message.put("role", "user");
            message.put("content", prompt);

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", geminiModel);
            requestBody.put("messages", Collections.singletonList(message));
            requestBody.put("max_tokens", 2048);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + geminiApiKey);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<Map> response = restTemplate.postForEntity(geminiApiUrl, entity, Map.class);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                Map body = response.getBody();
                List choices = (List) body.get("choices");
                if (choices != null && !choices.isEmpty()) {
                    Map choice = (Map) choices.get(0);
                    Map messageObj = (Map) choice.get("message");
                    if (messageObj != null) {
                        String rawContent = (String) messageObj.get("content");
                        if (rawContent != null) {
                            if (rawContent.contains("</think>")) {
                                rawContent = rawContent.substring(rawContent.indexOf("</think>") + 8).trim();
                            }
                            return rawContent;
                        }
                    }
                }
            }
        } catch (org.springframework.web.client.HttpStatusCodeException e) {
            log.error("HTTP error calling Groq API", e);
            String responseBody = e.getResponseBodyAsString();
            String fullMessage = responseBody + " " + e.getMessage();
            if (e.getStatusCode().value() == 429 || fullMessage.contains("rate_limit_exceeded")) {
                java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("Please try again in ([0-9\\.]+[a-zA-Z]*)");
                java.util.regex.Matcher matcher = pattern.matcher(fullMessage);
                if (matcher.find()) {
                    return "Rate limit reached. Please wait for " + matcher.group(1) + ".";
                }
                return "Rate limit reached. Please try again in a few seconds.";
            }
            return "Error calling Groq: " + e.getMessage();
        } catch (Exception e) {
            log.error("Error calling Groq API", e);
            return "Error calling Groq: " + e.getMessage();
        }
        return "No response from Groq API.";
    }
}
