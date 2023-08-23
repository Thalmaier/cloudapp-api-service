package info.thale.apiservice.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;

import static info.thale.apiservice.domain.UserRoles.ADMIN;
import static info.thale.apiservice.domain.UserRoles.USER;

@Configuration
public class SecurityConfig {

    @Bean
    public ReactiveAuthenticationManager reactiveAuthenticationManager(
            ReactiveUserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder
    ) {
        var authenticationManager = new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
        authenticationManager.setPasswordEncoder(passwordEncoder);
        return authenticationManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http, JwtTokenAuthService jwtTokenAuthService) {
        http
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .authorizeExchange(request ->
                        request.pathMatchers("/api/token").permitAll()
                )
                .authorizeExchange(request ->
                        request.pathMatchers("/api/admin/**").hasAnyAuthority(ADMIN.getAuthority())
                )
                .authorizeExchange(request ->
                        request.pathMatchers("/api/user").authenticated()
                )
                .authorizeExchange(request ->
                        request.pathMatchers("/api/user/{userId}/**").access(
                                new EndpointAuthorizationManager()
                                        .addAnyRequiredRole(USER, ADMIN)
                                        .addPathVariableValidator("userId", user -> user.userId().toString())
                        )
                )
                .authorizeExchange(request ->
                        request.pathMatchers("/api/public/**").permitAll()
                )
                .authorizeExchange(request ->
                        request.pathMatchers("/actuator/**").permitAll()
                );

        http.addFilterAt(new JwtTokenAuthenticationFilter(jwtTokenAuthService), SecurityWebFiltersOrder.HTTP_BASIC);

        return http.csrf(ServerHttpSecurity.CsrfSpec::disable).build();
    }

}
