package com.placementserver.placementserver.services;

import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import io.jsonwebtoken.JwtBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {
	
	private final int validTime = 5;
	
	 private static String secretkey = "";

	    public JWTService() {

	        try {
	            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
	            SecretKey sk = keyGen.generateKey();
	            secretkey = Base64.getEncoder().encodeToString(sk.getEncoded());
	        } catch (NoSuchAlgorithmException e) {
	            throw new RuntimeException(e);
	        }
	    }

	    public String generateToken(String username, String role, String purpose) {
	        Map<String, Object> claims = new HashMap<>();
			claims.put("user_name", username);
			claims.put("role", role);
	        return Jwts.builder()
					.claims(claims).subject(purpose)
					.issuedAt(new Date(System.currentTimeMillis()))
					.expiration(new Date(System.currentTimeMillis() + 1000 * 60 * validTime * 60))
	                .signWith(getKey())
	                .compact();
	    }

	    private SecretKey getKey() {
	        byte[] keyBytes = Decoders.BASE64.decode(secretkey);
	        return Keys.hmacShaKeyFor(keyBytes);
	    }

	    public String extractUserName(String token) {
			return extractClaim(token, claims -> claims.get("user_name", String.class));
	    }

		/*public String extractPurpose(String token) {
			return extractClaim(token, Claims::getSubject);  // Extracts the "sub" claim
		}*/

	    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
	        final Claims claims = extractAllClaims(token);
	        return claimResolver.apply(claims);
	    }

	    private Claims extractAllClaims(String token) {
	        return Jwts.parser()
	                .verifyWith(getKey())
	                .build()
	                .parseSignedClaims(token)
	                .getPayload();
	    }
	/*private Claims extractAllClaims(String token) {
		return Jwts.parser()
				.setSigningKey(getKey())  // Ensure the signing key matches the one used in token generation
				.build()
				.parseClaimsJws(token)    // Parse and validate the token
				.getBody();
	}*/



	public boolean validateToken(String token, UserDetails userDetails) {
		final String userName = extractUserName(token);
		return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	public boolean validateAndExtractRole(String token) {
		return isTokenExpired(token);
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public String generateResetToken(String userName, String role, String purpose) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("user_name", userName);
		claims.put("role", role);
		return Jwts.builder()
				.claims(claims).subject(purpose)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 10))   // 10 minutes of validity
				.signWith(getKey())
				.compact();
	}

	public Claims extractClaims(String token) {
		try {
			// Use the Jwts parser to parse the token and validate the signature with the secret key
			return Jwts.parser()
					.setSigningKey(getKey())  // Set the secret key used for signing
					.build()
					.parseClaimsJws(token)  // Parse the JWT token and validate
					.getBody();  // Extract and return the claims (payload) from the token
		} catch (Exception e) {
			throw new RuntimeException("Failed to extract claims from token", e);  // Handle any exception (invalid token, etc.)
		}
	}

}
