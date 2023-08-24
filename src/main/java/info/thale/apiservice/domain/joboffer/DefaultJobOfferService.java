package info.thale.apiservice.domain.joboffer;

import org.springframework.stereotype.Service;

import info.thale.apiservice.auth.model.AuthenticatedUser;
import info.thale.apiservice.domain.joboffer.model.CreateJobOfferDTO;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class DefaultJobOfferService implements JobOfferService {

    private final JobOfferAdapter jobOfferAdapter;

    @Override
    public Mono<Void> createJobOffer(CreateJobOfferDTO createJobOfferDTO, AuthenticatedUser user) {
        return jobOfferAdapter.createJobOffer(createJobOfferDTO, user);
    }

}
