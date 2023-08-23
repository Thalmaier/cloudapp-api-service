package info.thale.apiservice.api;

import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import info.thale.apiservice.api.model.LoginRequest;
import info.thale.apiservice.api.model.LoginResponse;
import info.thale.apiservice.auth.JwtTokenAuthService;
import info.thale.apiservice.auth.model.AuthenticatedUser;
import info.thale.apiservice.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class SessionController {

    private final JwtTokenAuthService tokenProvider;
    private final ReactiveAuthenticationManager authenticationManager;


    @PostMapping("/api/token")
    public Mono<ResponseEntity<LoginResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
        return authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password()))
                .map(tokenProvider::createJwtToken)
                .map(jwt -> {
                            HttpHeaders httpHeaders = new HttpHeaders();
                            httpHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + jwt);
                            return new ResponseEntity<>(new LoginResponse(jwt), httpHeaders, HttpStatus.OK);
                        }
                );
    }

    @GetMapping("/api/user")
    public Mono<ResponseEntity<AuthenticatedUser>> userData() {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .map(AuthenticatedUser.class::cast)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK));
    }


}
