package info.thale.apiservice.domain;

import org.springframework.security.core.GrantedAuthority;

public enum UserRoles implements GrantedAuthority {

    USER,

    ADMIN;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
