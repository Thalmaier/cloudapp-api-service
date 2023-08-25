package info.thale.apiservice.api;

import java.util.Arrays;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.zalando.problem.DefaultProblem;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.StatusType;
import org.zalando.problem.ThrowableProblem;
import org.zalando.problem.spring.web.advice.ProblemHandling;

@ControllerAdvice
public class ApiExceptionHandler implements ProblemHandling {

    @ExceptionHandler(value = {UsernameNotFoundException.class, BadCredentialsException.class})
    public ResponseEntity<Void> handleUserNotFoundException(Exception e, WebRequest request) {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<Problem> defaultExceptionHandler(Exception e, WebRequest request) {
        var problem = Problem.builder()
                .withStatus(Status.INTERNAL_SERVER_ERROR)
                .withDetail(e.getMessage())
                .build();

        e.printStackTrace();

        return new ResponseEntity<>(problem, HttpStatus.valueOf(Objects.requireNonNull(problem.getStatus()).getStatusCode()));
    }

}
