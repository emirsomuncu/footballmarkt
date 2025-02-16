package com.somuncu.footballmarkt.security.webtoken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@Service
public class JwtService {

    private static final String SECRET = "3510A94A16B8B7492A5C61FA9E79E3C1AE4B8E5A4322B2A4C4A50F6E174FD7C8B155D768405FEBEF31CFD1D5CA0AD6030939D89940E47EA4F83BAB2DDC0D5A20";
    private static final Long VALIDITY = TimeUnit.MINUTES.toMillis(30);

    private SecretKey convertSecretKey() {
        byte[] decodeKey = Base64.getDecoder().decode(SECRET);
        return Keys.hmacShaKeyFor(decodeKey);
    }

    public String generateToken(UserDetails userDetails) {

        HashMap<String,String> claims = new HashMap<>();
        claims.put("iss" , "https://besiktas.com");

        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusMillis(VALIDITY)))
                .signWith(convertSecretKey())
                .compact();
    }

    public String extractUsername(String jwt) {

        Claims claims = Jwts.parser()
                .verifyWith(convertSecretKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
        return claims.getSubject();
    }

    public boolean isTokenValid(String jwt) {

         Claims claims = Jwts.parser()
                .verifyWith(convertSecretKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
         return claims.getExpiration().after(Date.from(Instant.now()));
    }

}
