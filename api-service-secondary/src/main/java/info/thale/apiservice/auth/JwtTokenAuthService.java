package info.thale.apiservice.secondary.auth;

import java.time.Instant;
import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import info.thale.apiservice.core.domain.UserId;
import info.thale.apiservice.secondary.auth.model.AuthUserDetails;
import info.thale.apiservice.secondary.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

@Component
public class JwtTokenAuthService {

    public static final String JWT_USER_ID_CLAIM_NAME = "user_id";

    private final JwtConfig jwtConfig;

    private final UserRepository userRepository;

    public JwtTokenAuthService(JwtConfig jwtConfig, UserRepository userRepository) {
        this.jwtConfig = jwtConfig;
        this.userRepository = userRepository;
    }

    public String createJwtToken(Authentication authentication) {
        if (!(authentication.getPrincipal() instanceof AuthUserDetails userDetails)) {
            return null;
        }

        Claims claims = Jwts.claims().setSubject(userDetails.userId().toString());
        claims.put(JWT_USER_ID_CLAIM_NAME, userDetails.userId().toString());

        var now = Instant.now();

        return jwtConfig.getJwtBuilder()
                .setClaims(claims)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(jwtConfig.getValidityInSeconds())))
                .compact();
    }

    public Authentication getAuthenticationFromJwtToken(String token) {
        Claims claims = jwtConfig.getJwtParser()
                .parseClaimsJws(token)
                .getBody();

        return new AuthUserDetails(
                new UserId(claims.get(JWT_USER_ID_CLAIM_NAME).toString()),
                userRepository.findUserByEmail(claims.getSubject()).roles(),
                token
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
