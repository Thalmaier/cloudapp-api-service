package info.thale.apiservice.secondary.auth.model;

import java.util.Collection;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import info.thale.apiservice.core.domain.UserId;
import info.thale.apiservice.core.domain.UserRoles;

public record AuthUserDetails(UserId userId, List<UserRoles> userRoles, @JsonIgnore String jwtToken)
        implements Authentication, UserDetails {

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userRoles();
    }

    @Override
    @Nullable
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return userId.toString();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    @JsonIgnore
    public String getCredentials() {
        return jwtToken();
    }

    @Override
    @JsonIgnore
    public AuthUserDetails getDetails() {
        return this;
    }

    @Override
    @JsonIgnore
    public UserId getPrincipal() {
        return userId();
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
        return userId().toString();
    }
}
