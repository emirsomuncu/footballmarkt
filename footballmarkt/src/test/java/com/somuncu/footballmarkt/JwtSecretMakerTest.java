package com.somuncu.footballmarkt;

import io.jsonwebtoken.Jwts;
import jakarta.xml.bind.DatatypeConverter;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;

public class JwtSecretMakerTest {

    @Test
    public void generateSecretKey() {

        SecretKey secretKey = Jwts.SIG.HS512.key().build();
        String encodedKey = DatatypeConverter.printHexBinary(secretKey.getEncoded());

        System.out.printf("\nSecret Key : [%s]\n" , encodedKey);
    }

}
