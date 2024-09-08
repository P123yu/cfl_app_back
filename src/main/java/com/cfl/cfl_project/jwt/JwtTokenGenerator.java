package com.cfl.cfl_project.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.Collection;
import java.util.function.Function;

@Component
public class JwtTokenGenerator {

    @Value("${Secret_String}")
    public String my_secretKey;

    // generate Jwt Token

    public String generateToken(UserDetails user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("authorities", getAuthorities(user.getAuthorities()))
                .setIssuedAt(Date.from(Instant.now()))  // Current time
                .setExpiration(Date.from(Instant.now().plus(90, ChronoUnit.DAYS)))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    public String getAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<String> setAuth = new HashSet<>();
        for (GrantedAuthority grantAuth : authorities) {
            // adding the string of role "mgr" ,"usr" ,"mgr"
            setAuth.add(grantAuth.getAuthority());   // [ROLE_mgr,ROLE_usr]   => it is collection of HashSet
        }
        return String.join(",", setAuth);  // convert the HashSet to string separated by comma
    }



    // convert hexadecimal string into bits of array

    public Key getKey(){
        byte []secretArray=Decoders.BASE64.decode(my_secretKey);
        return Keys.hmacShaKeyFor(secretArray);
    }




    // filter   ======================================================================================

    // extract all claims present in token
    private Claims extractAllClaims(String token){
        return Jwts.parser().setSigningKey(getKey())
                .build().parseClaimsJws(token).getBody();
    }

    // token => extractAll claims => resolve only subject claim (username)  [extractUserName]

    // i am extracting all the claims from the token
    public <T> T extractClaims(String token, Function<Claims,T> claimsResolver){
        Claims claims=extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // and from those claims i want only subject that is userName
    public String extractUsername(String token){
        return extractClaims(token,Claims::getSubject);
    }

}