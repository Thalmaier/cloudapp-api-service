package info.thale.apiservice.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import info.thale.apiservice.api.auth.ContextAuthenticationService;
import info.thale.apiservice.api.generated.JobOfferApi;
import info.thale.apiservice.api.generated.model.JobOfferCreationRequestDTO;
import info.thale.apiservice.domain.joboffer.JobOfferService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class JobOfferController implements JobOfferApi {

    private final JobOfferService jobOfferService;
    private final JobOfferRequestMapper jobOfferRequestMapper;

    @Override
    public ResponseEntity<Void> createJobOffer(JobOfferCreationRequestDTO jobOfferCreationRequestDTO) {
        var authenticatedUser = ContextAuthenticationService.getUserFromContextOrThrow();
        var createJobOfferDTO = jobOfferRequestMapper.toJobOffer(jobOfferCreationRequestDTO);

        if (authenticatedUser == null) {

        }

        jobOfferService.createJobOffer(createJobOfferDTO, authenticatedUser.userId(), authenticatedUser.userRoles());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
