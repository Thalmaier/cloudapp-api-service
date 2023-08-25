package info.thale.apiservice.domain.exception;

import org.springframework.http.HttpStatus;

public class NoAuthenticatedUserFoundException extends IdentifiedRuntimeException {

    public NoAuthenticatedUserFoundException(HttpStatus httpStatus) {
        super(httpStatus);
    }

}
