package info.thale.apiservice.domain;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
public record User(
        @Id UserId id,
        @Indexed String email,
        String encryptedPassword,
        List<UserRoles> roles
) {

    @Override
    public String toString() {
        return "User{" +
                "id=" + id.id().toString() +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                '}';
    }

}
