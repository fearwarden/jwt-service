package guide.jwtservice.authentication.service.implementations;

import guide.jwtservice.authentication.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.secret.key}")
    private String jwtSecretKey;

    // ------------------------------------------
    // Public Methods
    // ------------------------------------------

    /**
     * Extracts the username (email) from the provided JWT token.
     *
     * @param token The JWT token.
     * @return The username (email) embedded in the token.
     */
    @Override
    public String extractUserName(String token) {
        return this.extractClaim(token, Claims::getSubject); // The subject field of JWT typically contains the user's identifier, e.g., email.
    }

    /**
     * Generates a JWT token for the provided user details.
     *
     * @param userDetails Details of the user for whom the token needs to be generated.
     * @return The generated JWT token.
     */
    @Override
    public String generateToken(UserDetails userDetails) {
        return this.generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Validates if the JWT token corresponds to the provided user details.
     *
     * @param token The JWT token.
     * @param userDetails Details of the user to validate against.
     * @return true if token matches the provided user details, false otherwise.
     */
    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = this.extractUserName(token);
        return userName.equals(userDetails.getUsername());
    }

    /**
     * Checks if the provided JWT token has expired.
     *
     * @param token The JWT token.
     * @return true if the token is expired, false otherwise.
     */
    @Override
    public boolean isTokenExpired(String token) {
        return this.extractExpiration(token).before(new Date());
    }

    /**
     * Generate a new refresh token.
     *
     * @return the refresh token.
     */
    @Override
    public String generateRefreshToken() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[32];
        secureRandom.nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }

    // ------------------------------------------
    // Private Helper Methods
    // ------------------------------------------

    /**
     * Generates a JWT token with additional claims for the provided user details.
     *
     * @param extraClaims Additional claims to embed in the token.
     * @param userDetails Details of the user for whom the token needs to be generated.
     * @return The generated JWT token.
     */
    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 15))
                .signWith(this.getSigninKey(), SignatureAlgorithm.HS256).compact();
    }

    /**
     * Extracts the expiration date from the provided JWT token.
     *
     * @param token The JWT token.
     * @return The expiration date embedded in the token.
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extracts a specific claim from the JWT token using the provided claim resolver function.
     *
     * @param token The JWT token.
     * @param claimsResolvers Function to extract a specific claim from token claims.
     * @return The extracted claim value.
     */
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = this.extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    /**
     * Extracts all claims from the provided JWT token.
     *
     * @param token The JWT token.
     * @return All the claims embedded in the token.
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(this.getSigninKey()).build()
                .parseClaimsJws(token).getBody();
    }

    /**
     * Retrieves the signing key used for token generation and validation.
     *
     * @return The signing key.
     */
    private Key getSigninKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.jwtSecretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
