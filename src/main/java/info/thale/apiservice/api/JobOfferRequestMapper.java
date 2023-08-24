package info.thale.apiservice.api;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import info.thale.apiservice.api.generated.model.ApiJobOffersPostRequestDTO;

import info.thale.apiservice.domain.joboffer.model.CreateJobOfferDTO;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface JobOfferRequestMapper {

    CreateJobOfferDTO toJobOffer(ApiJobOffersPostRequestDTO requestDTO);


}
