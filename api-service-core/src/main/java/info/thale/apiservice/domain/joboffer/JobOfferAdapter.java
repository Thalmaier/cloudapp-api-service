package info.thale.apiservice.core.domain.joboffer;

import java.util.List;

import info.thale.apiservice.core.domain.UserId;
import info.thale.apiservice.core.domain.UserRoles;
import info.thale.apiservice.core.domain.joboffer.model.CreateJobOfferDTO;

public interface JobOfferAdapter {

     void createJobOffer(CreateJobOfferDTO jobOffer,  UserId userId, List<UserRoles> userRoles);

}
