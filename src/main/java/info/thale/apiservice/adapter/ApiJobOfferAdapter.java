package info.thale.apiservice.adapter;

import org.springframework.stereotype.Service;

import info.thale.apiservice.auth.model.AuthenticatedUser;
import info.thale.apiservice.domain.joboffer.JobOfferAdapter;
import info.thale.apiservice.domain.joboffer.model.CreateJobOfferDTO;
import reactor.core.publisher.Mono;

@Service
public class ApiJobOfferAdapter implements JobOfferAdapter {

    @Override
    public Mono<Void> createJobOffer(CreateJobOfferDTO jobOffer, AuthenticatedUser user) {
        return Mono.empty();
    }
}
