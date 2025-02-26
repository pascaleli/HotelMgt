package dev.pascal.HotelManagement.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.PublicKey;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Service
public class JWTUtills {

    private static final long EXPIRATION_TIME = 1000 * 60 *24 *7; // FOR 7 DAYS

        private final SecretKey key;

        public JWTUtills(){
            String secreteString = "yy5349y9y349yy34y5834y78higfeuwh838y4y3834687438778yiuhfguhgfe8y84y68248rjnrn";
           // byte[] keyBytes = Base64.getDecoder().decode(secreteString.getBytes(StandardCharsets.UTF_8));
            byte[] keyBytes = secreteString.getBytes(StandardCharsets.UTF_8);

            this.key = new SecretKeySpec(keyBytes,"HmacSHA256");
            
        }
        
        public String generateToken(UserDetails userDetails){
            return Jwts.builder()
                    .subject(userDetails.getUsername())
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .signWith(key)
                    .compact();
        }


        public String extractUsername(String token){
            return extractClims(token, Claims::getSubject);
        }
        private <T> T extractClims(String token, Function<Claims, T> claimsTFunction){
            return claimsTFunction.apply(Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload());
        }

        public boolean isValidToken(String token, UserDetails userDetails){
            final String username = extractUsername(token);
            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        }

        private boolean isTokenExpired(String token){
            return extractClims(token, Claims::getExpiration).before(new Date());
        }


}
