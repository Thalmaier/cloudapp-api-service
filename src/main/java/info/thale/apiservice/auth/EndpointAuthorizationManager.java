package info.thale.apiservice.auth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;

import info.thale.apiservice.auth.model.AuthenticatedUser;
import info.thale.apiservice.domain.UserRoles;
import reactor.core.publisher.Mono;

public class EndpointAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    private final List<UserRoles> anyRequiredUserRoles = new ArrayList<>();
    private final List<EndpointValidator> validators = new ArrayList<>();

    interface EndpointValidator {
        boolean validate(AuthenticatedUser user, AuthorizationContext context);
    }

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext context) {
        return authentication
                .filter(AuthenticatedUser.class::isInstance)
                .cast(AuthenticatedUser.class)
                .filter(this::hasAnyRequiredUserRole)
                .filter(user -> endpointValidationSuccessful(context, user))
                .map(user -> new AuthorizationDecision(true))
                .defaultIfEmpty(new AuthorizationDecision(false));
    }

    private boolean endpointValidationSuccessful(AuthorizationContext context, AuthenticatedUser user) {
        return validators.stream().anyMatch(validator -> validator.validate(user, context));
    }

    private boolean hasAnyRequiredUserRole(AuthenticatedUser user) {
        return anyRequiredUserRoles.isEmpty()
                || anyRequiredUserRoles.stream().anyMatch(requiredRole -> user.getAuthorities().contains(requiredRole));
    }


    public EndpointAuthorizationManager addAnyRequiredRole(UserRoles... roles) {
        anyRequiredUserRoles.addAll(Arrays.stream(roles).toList());
        return this;
    }

    public EndpointAuthorizationManager addPathVariableValidator(String pathVarName, Function<AuthenticatedUser, Object> function) {
        validators.add((user, context) -> context.getVariables().get(pathVarName).equals(function.apply(user)));
        return this;
    }
}
