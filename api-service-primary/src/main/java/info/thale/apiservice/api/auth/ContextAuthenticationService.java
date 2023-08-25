package info.thale.apiservice.api;

import javax.annotation.Nullable;

import org.springframework.security.core.context.SecurityContextHolder;

import info.thale.apiservice.auth.model.AuthUserDetails;


public class AuthenticationService {

    private AuthenticationService(){}

    @Nullable
    public static AuthUserDetails getUserFromContext() {
        if (SecurityContextHolder.getContext().getAuthentication() instanceof AuthUserDetails authUserDetails) {
            return authUserDetails;
        }

        return null;
    }

}
