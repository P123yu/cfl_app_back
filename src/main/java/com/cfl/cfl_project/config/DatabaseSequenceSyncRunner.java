package com.cfl.cfl_project.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class DatabaseSequenceSyncRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DatabaseSequenceSyncRunner.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        log.info("Starting database sequence synchronization...");
        try {
            // Find all tables and columns in the 'public' schema that have an associated sequence generator
            String findSequencesSql = 
                "SELECT t.relname AS table_name, a.attname AS column_name " +
                "FROM pg_class t " +
                "JOIN pg_attribute a ON a.attrelid = t.oid " +
                "JOIN pg_namespace n ON n.oid = t.relnamespace " +
                "WHERE t.relkind = 'r' " +
                "  AND n.nspname = 'public' " +
                "  AND a.attnum > 0 " +
                "  AND NOT a.attisdropped " +
                "  AND pg_get_serial_sequence('\"' || n.nspname || '\".\"' || t.relname || '\"', a.attname) IS NOT NULL";

            List<Map<String, Object>> rows = jdbcTemplate.queryForList(findSequencesSql);
            log.info("Found {} columns with sequence generators to synchronize", rows.size());

            for (Map<String, Object> row : rows) {
                String tableName = (String) row.get("table_name");
                String columnName = (String) row.get("column_name");

                if (tableName != null && columnName != null) {
                    try {
                        // Dynamically update the sequence to match the max value of the ID column
                        String syncSql = String.format(
                            "SELECT setval(pg_get_serial_sequence('\"public\".\"%s\"', '%s'), COALESCE(MAX(\"%s\"), 1)) FROM \"public\".\"%s\"",
                            tableName, columnName, columnName, tableName
                        );
                        jdbcTemplate.execute(syncSql);
                        log.info("Successfully synchronized sequence for table '{}', column '{}'", tableName, columnName);
                    } catch (Exception e) {
                        log.error("Failed to sync sequence for table '{}', column '{}': {}", tableName, columnName, e.getMessage());
                    }
                }
            }
            log.info("Database sequence synchronization completed successfully.");
        } catch (Exception e) {
            log.error("Error occurred during database sequence synchronization: {}", e.getMessage(), e);
        }
    }
}
