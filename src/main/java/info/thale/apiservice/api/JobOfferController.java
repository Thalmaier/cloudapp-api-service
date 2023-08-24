package info.thale.apiservice.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.server.ServerWebExchange;

import info.thale.apiservice.api.generated.JobOfferApi;
import info.thale.apiservice.api.generated.model.ApiJobOffersPostRequestDTO;
import info.thale.apiservice.domain.joboffer.JobOfferService;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@Controller
@AllArgsConstructor
public class JobOfferController implements JobOfferApi {

    private final JobOfferService jobOfferService;
    private final JobOfferRequestMapper jobOfferRequestMapper;

    @Override
    public Mono<ResponseEntity<Void>> apiJobOffersPost(
            Mono<ApiJobOffersPostRequestDTO> apiJobOffersPostRequestDTO,
            ServerWebExchange exchange) {

        var authenticatedUser = AuthenticationService.getUserFromContext();
        var createJobOfferDTO = apiJobOffersPostRequestDTO.map(jobOfferRequestMapper::toJobOffer);

        return createJobOfferDTO.zipWith(authenticatedUser).flatMap(
                        zipped -> jobOfferService.createJobOffer(zipped.getT1(), zipped.getT2())
                ).thenReturn(new ResponseEntity<>(HttpStatus.ACCEPTED));
    }
}
