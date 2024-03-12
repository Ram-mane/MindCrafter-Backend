package com.user.bloggs.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtTokenHelper {


    //requirement :
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    //    public static final long JWT_TOKEN_VALIDITY =  60;
//    private String secret = "jwtTokenKey";
   private SecretKey secret = Jwts.SIG.HS256.key().build(); //or HS384.key() or HS512.key()
    
    
//    private SecretKey secret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(key));
    
    
    //retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //for retrieveing any information from token we will need the secret key
    Claims getAllClaimsFromToken(String token) {
		         return Jwts.parser()
		                .verifyWith(secret)
		                .build()
		                .parseSignedClaims(token)
		                .getPayload();
    	
//    	return (Claims) Jwts.parser().verifyWith(secret).build().parseSignedClaims(token);
    }

   

	//check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //generate token for user
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    //while creating the token -
    private String doGenerateToken(Map<String, Object> claims, String username) {

        return Jwts.builder().claims(claims).subject(username).issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(secret).compact();
        
//        return Jwts.builder().subject(username).signWith(secret).compact();
    }

    //validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
	
}
