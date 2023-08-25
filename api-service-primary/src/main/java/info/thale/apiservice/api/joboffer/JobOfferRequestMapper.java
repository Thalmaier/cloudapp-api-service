package info.thale.apiservice.api;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import info.thale.apiservice.api.generated.model.JobOfferCreationRequestDTO;
import info.thale.apiservice.domain.joboffer.model.CreateJobOfferDTO;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = MonetaryAmountMapper.class)
public interface JobOfferRequestMapper {

    CreateJobOfferDTO toJobOffer(JobOfferCreationRequestDTO requestDTO);


}
