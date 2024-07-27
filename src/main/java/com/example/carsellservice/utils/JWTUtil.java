package com.example.carsellservice.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JWTUtil {

    private static Key getSigningKey() {
        String SECRET = System.getenv("SECRET"); //this takes a secret of 256bits which is in base10 cause of HMCA Sha algorithm
        byte[] keyBytes = Decoders.BASE64.decode(SECRET); //decode the secret to base64 using the Decorders class
        return Keys.hmacShaKeyFor(keyBytes); //this the key
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody(); //this body contains the the payload information in the token
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimResolvers) {
        final Claims claims = extractAllClaims(token); //Claims constitute the payload of the json web token and represent a set of information exchanged between two parties
        return claimResolvers.apply(claims);
    }

    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);// this returns the username
    }

    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration); //return the expiration of the token
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());//this will return a boolean if the token is expired or not
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token); // this variable will hold the username extracted from the payload of the token
        return (username.equals(userDetails.getUsername()) && isTokenExpired(token));//if the username in payload is the same as one in the db and the token is not expired a boolean of true is returned else its false
    }

    private static String generateToken(Map<String, Object> extractClaims, UserDetails userDetails) {
        //this function generates a token by setting the payload, setting the alogrithm used and the expiration dates of the token
        return Jwts.builder().setClaims(extractClaims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }

    public static String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }
}
