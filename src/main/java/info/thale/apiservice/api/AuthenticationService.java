package info.thale.apiservice.api;

import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;

import info.thale.apiservice.auth.model.AuthenticatedUser;
import info.thale.apiservice.domain.User;
import reactor.core.publisher.Mono;

public class AuthenticationService {

    public static Mono<AuthenticatedUser> getUserFromContext() {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .filter(AuthenticatedUser.class::isInstance)
                .map(AuthenticatedUser.class::cast);
    }

}
