package info.thale.apiservice.secondary.adapter;

import java.util.List;

import org.springframework.stereotype.Service;

import info.thale.apiservice.core.domain.UserId;
import info.thale.apiservice.core.domain.UserRoles;
import info.thale.apiservice.core.domain.joboffer.JobOfferAdapter;
import info.thale.apiservice.core.domain.joboffer.model.CreateJobOfferDTO;

@Service
public class ApiJobOfferAdapter implements JobOfferAdapter {

    @Override
    public void createJobOffer(CreateJobOfferDTO jobOffer, UserId userId, List<UserRoles> userRoles) {
        return;
    }
}
