package info.thale.apiservice.core.domain.joboffer;

import java.util.List;

import org.springframework.stereotype.Service;

import info.thale.apiservice.core.domain.UserId;
import info.thale.apiservice.core.domain.UserRoles;
import info.thale.apiservice.core.domain.joboffer.model.CreateJobOfferDTO;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DefaultJobOfferService implements JobOfferService {

    private final JobOfferAdapter jobOfferAdapter;

    @Override
    public void createJobOffer(CreateJobOfferDTO createJobOfferDTO, UserId userId, List<UserRoles> userRoles) {
        jobOfferAdapter.createJobOffer(createJobOfferDTO, userId, userRoles);
    }

}
