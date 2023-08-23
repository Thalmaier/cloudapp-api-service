package info.thale.apiservice.domain;

import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class User {

    @Id private final UUID id;
    @Indexed private final String email;
    private final String encryptedPassword;
    private final List<UserRoles> roles;

}
