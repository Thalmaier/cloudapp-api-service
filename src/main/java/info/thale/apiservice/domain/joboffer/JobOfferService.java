package info.thale.apiservice.domain.joboffer;

import info.thale.apiservice.auth.model.AuthenticatedUser;
import info.thale.apiservice.domain.joboffer.model.CreateJobOfferDTO;
import reactor.core.publisher.Mono;

public interface JobOfferService {

    Mono<Void> createJobOffer(CreateJobOfferDTO createJobOfferDTO, AuthenticatedUser user);

}
