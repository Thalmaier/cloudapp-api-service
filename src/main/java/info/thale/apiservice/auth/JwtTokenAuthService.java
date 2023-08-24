package info.thale.apiservice.auth;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import info.thale.apiservice.auth.model.AuthenticatedUser;
import info.thale.apiservice.auth.model.FoundUserDetails;
import info.thale.apiservice.domain.User;
import info.thale.apiservice.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import reactor.core.publisher.Mono;

@Component
public class JwtTokenAuthService {

    public static final String JWT_USER_ID_CLAIM_NAME = "user_id";

    private final JwtConfig jwtConfig;

    private final UserRepository userRepository;

    private SecretKey secretKey;

    public JwtTokenAuthService(JwtConfig jwtConfig, UserRepository userRepository) {
        this.jwtConfig = jwtConfig;
        this.userRepository = userRepository;
    }

    @PostConstruct
    protected void init() {
        var secret = Base64.getEncoder().encodeToString(jwtConfig.getSecretKey().getBytes());
        secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String createJwtToken(Authentication authentication) {
        if (!(authentication.getPrincipal() instanceof FoundUserDetails userDetails)) {
            return null;
        }

        Claims claims = Jwts.claims().setSubject(userDetails.email());
        claims.put(JWT_USER_ID_CLAIM_NAME, userDetails.userId());

        var now = Instant.now();

        return jwtConfig.getJwtBuilder()
                .setClaims(claims)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(jwtConfig.getValidityInSeconds())))
                .compact();
    }

    public Mono<Authentication> getAuthenticationFromJwtToken(String token) {
        Claims claims = jwtConfig.getJwtParser()
                .parseClaimsJws(token)
                .getBody();

        return userRepository.findUserByEmail(claims.getSubject())
                .map(User::roles)
                .map(authorities ->
                        new AuthenticatedUser(
                                UUID.fromString(claims.get(JWT_USER_ID_CLAIM_NAME).toString()),
                                claims.getSubject(),
                                authorities,
                                token
                        )
                );
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = jwtConfig.getJwtParser().parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

}
