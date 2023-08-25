package info.thale.apiservice.api;

import info.thale.apiservice.api.generated.model.UserResponseDTO;
import info.thale.apiservice.domain.User;

public class UserMapper {

    public static UserResponseDTO mapToDTO(User user) {
        return new UserResponseDTO(
                user.id().id(),
                user.email(),
                user.firstName(),
                user.lastName(),
                user.roles().stream().map(Enum::name).toList()
        );
    }

}
