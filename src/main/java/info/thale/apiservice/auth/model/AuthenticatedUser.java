package info.thale.apiservice.auth.model;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;

import info.thale.apiservice.domain.UserRoles;

public record AuthenticatedUser(UUID userId, String email, List<UserRoles> userRoles, @JsonIgnore String jwtToken) implements Authentication {


    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userRoles();
    }

    @Override
    @JsonIgnore
    public String getCredentials() {
        return jwtToken();
    }

    @Override
    @JsonIgnore
    public AuthenticatedUser getDetails() {
        return this;
    }

    @Override
    @JsonIgnore
    public String getPrincipal() {
        return email();
    }

    @Override
    @JsonIgnore
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        throw new IllegalCallerException();
    }

    @Override
    @JsonIgnore
    public String getName() {
        return email();
    }
}
