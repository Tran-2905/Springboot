package com.example.shopapp.Component;

import com.example.shopapp.Model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JWTTokenUtil {
    @Value("${jwt.expiration}")
    private int expiration;
    @Value("${jwt.secretKey}")
    private String secretKey;
    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("phoneNumber", user.getPhoneNumber());
        try{
            String token = Jwts.builder().setClaims(claims)
                    .setSubject(user.getPhoneNumber())
                    .setExpiration(new Date(System.currentTimeMillis() + expiration*1000L))
                    .signWith(getSigninKey(), SignatureAlgorithm.HS256)
                    .compact();
            return token;
        } catch (Exception e) {
            System.err.println("cannot create jwt token"+ e.getMessage());
            return null;
        }
    }
    private Key getSigninKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaim(String token) {
        return
                Jwts
                .parser()
                .setSigningKey(getSigninKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver){
        final Claims claims= this.extractAllClaim(token);
        return claimsResolver.apply(claims);
    }
    //check expiration
    public boolean isTokenExpired(String token) {
       Date experationDate = this.extractClaims(token,Claims::getExpiration);
        return experationDate.before(new Date());
    }

}
