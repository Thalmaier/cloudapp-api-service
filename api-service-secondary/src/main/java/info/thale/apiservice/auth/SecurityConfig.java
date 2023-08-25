package info.thale.apiservice.secondary.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.RequiredArgsConstructor;

import static info.thale.apiservice.core.domain.UserRoles.ADMIN;
import static info.thale.apiservice.core.domain.UserRoles.USER;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenAuthService tokenProvider;

    @Bean
    public AuthenticationProvider daoAuthenticationProvider(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(request ->
                request.requestMatchers("/api/token").permitAll()
        ).authorizeHttpRequests(request ->
                        request.requestMatchers("/api/admin/**").hasAnyAuthority(ADMIN.getAuthority())
                )
                .authorizeHttpRequests(request ->
                        request.requestMatchers("/api/user").authenticated()
                )
                .authorizeHttpRequests(request ->
                        request.requestMatchers("/api/user/{userId}/**").access(
                                new EndpointAuthorizationManager()
                                        .addAnyRequiredRole(USER, ADMIN)
                                        .addPathVariableValidator("userId", user -> user.userId().toString())
                        )
                )
                .authorizeHttpRequests(request ->
                        request.requestMatchers("/api/public/**").permitAll()
                )
                .authorizeHttpRequests(request ->
                        request.requestMatchers("/actuator/**").permitAll()
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        http.addFilterAt(new JwtTokenAuthenticationFilter(tokenProvider), BasicAuthenticationFilter.class);

        return http.csrf(AbstractHttpConfigurer::disable).build();
    }


}
