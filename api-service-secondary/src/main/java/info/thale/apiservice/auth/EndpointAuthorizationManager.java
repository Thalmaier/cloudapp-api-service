package info.thale.apiservice.secondary.auth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.server.authorization.AuthorizationContext;

import info.thale.apiservice.core.domain.UserRoles;
import info.thale.apiservice.secondary.auth.model.AuthUserDetails;

public class EndpointAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    private final List<UserRoles> anyRequiredUserRoles = new ArrayList<>();
    private final List<EndpointValidator> validators = new ArrayList<>();

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
        if (authentication.get() instanceof AuthUserDetails authUserDetails) {
            return new AuthorizationDecision(
                    hasAnyRequiredUserRole(authUserDetails) && endpointValidationSuccessful(authUserDetails, context)
            );
        }

        return new AuthorizationDecision(false);
    }

    interface EndpointValidator {
        boolean validate(AuthUserDetails user, RequestAuthorizationContext context);
    }

    private boolean endpointValidationSuccessful(AuthUserDetails user, RequestAuthorizationContext context) {
        return validators.stream().anyMatch(validator -> validator.validate(user, context));
    }

    private boolean hasAnyRequiredUserRole(AuthUserDetails user) {
        return anyRequiredUserRoles.isEmpty()
                || anyRequiredUserRoles.stream().anyMatch(requiredRole -> user.getAuthorities().contains(requiredRole));
    }


    public EndpointAuthorizationManager addAnyRequiredRole(UserRoles... roles) {
        anyRequiredUserRoles.addAll(Arrays.stream(roles).toList());
        return this;
    }

    public EndpointAuthorizationManager addPathVariableValidator(String pathVarName, Function<AuthUserDetails, Object> function) {
        validators.add((user, context) -> context.getVariables().get(pathVarName).equals(function.apply(user)));
        return this;
    }
}
