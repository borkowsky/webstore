package net.rewerk.webstore.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import net.rewerk.webstore.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class JWTService {
    private final String issuer;
    private final Integer lifetime;
    private final String secret;
    private final JWTVerifier verifier;

    @Autowired
    public JWTService(
            @Value("${jwt.issuer}") String issuer,
            @Value("${jwt.lifetime}") Integer lifetime,
            @Value("${jwt.sign}") String secret
    ) {
        this.issuer = issuer;
        this.lifetime = lifetime;
        this.secret = secret;
        verifier = JWT.require(Algorithm.HMAC256(secret))
                .withIssuer(issuer)
                .build();
    }

    public String generateToken(UserDetails userDetails) {
        Date expiresIn = Date.from(Instant.now()
                .plus(
                        lifetime,
                        ChronoUnit.DAYS
                ));
        com.auth0.jwt.JWTCreator.Builder builder = JWT.create()
                .withClaim("NAME", userDetails.getUsername())
                .withIssuer(issuer)
                .withIssuedAt(new Date().toInstant())
                .withExpiresAt(expiresIn);
        if (userDetails instanceof User user) {
            builder.withClaim("UID", user.getId());
            builder.withClaim("ROLE", user.getRole().name());
        }
        return builder.sign(Algorithm.HMAC256(secret));
    }

    public String extractUsername(String token) {
        try {
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaim("NAME").asString();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            DecodedJWT jwt = verifier.verify(token);
            String username = jwt.getClaim("NAME").asString();
            if (username == null) return false;
            Date expiresAt = jwt.getExpiresAt();
            return (username.equals(userDetails.getUsername())) && !expiresAt.before(Date.from(Instant.now()));
        } catch (Exception e) {
            return false;
        }
    }
}
