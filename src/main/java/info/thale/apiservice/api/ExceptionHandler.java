package info.thale.apiservice.api;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.zalando.problem.spring.webflux.advice.ProblemHandling;

@ControllerAdvice
public class ExceptionHandler implements ProblemHandling {

}
