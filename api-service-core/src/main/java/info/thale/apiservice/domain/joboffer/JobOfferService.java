package info.thale.apiservice.core.domain.joboffer;

import java.util.List;

import info.thale.apiservice.core.domain.UserId;
import info.thale.apiservice.core.domain.UserRoles;
import info.thale.apiservice.core.domain.joboffer.model.CreateJobOfferDTO;

public interface JobOfferService {

    void createJobOffer(CreateJobOfferDTO createJobOfferDTO, UserId userId, List<UserRoles> userRoles);

}
