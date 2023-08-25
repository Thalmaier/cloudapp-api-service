package info.thale.apiservice.secondary.auth;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.boot.context.properties.ConfigurationProperties;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;


@ConfigurationProperties(prefix = "jwt")
@Getter
public class JwtConfig {

    private final String secretKey;
    private final long validityInSeconds;
    private final JwtParser jwtParser;
    private final JwtBuilder jwtBuilder;

    public JwtConfig(String secretKey, long validityInSeconds) {
        this.secretKey = secretKey;
        this.validityInSeconds = validityInSeconds;
        var encodedSecret = Base64.getEncoder().encodeToString(secretKey.getBytes());
        var jwtKey = Keys.hmacShaKeyFor(encodedSecret.getBytes(StandardCharsets.UTF_8));
        this.jwtParser = Jwts.parserBuilder().setSigningKey(jwtKey).build();
        this.jwtBuilder = Jwts.builder().signWith(jwtKey, SignatureAlgorithm.HS256);
    }
}
