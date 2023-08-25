package info.thale.apiservice.core.domain;

import java.util.UUID;

public record UserId(
        UUID id
) {

    public UserId(String id)  {
        this(UUID.fromString(id));
    }

    @Override
    public String toString() {
        return id().toString();
    }
}
