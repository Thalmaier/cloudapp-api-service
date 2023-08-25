package info.thale.apiservice.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public record User(
        @Id UserId id,
        @Indexed String email,
        String encryptedPassword,
        List<UserRoles> roles,
        String firstName,
        String lastName
) {}
