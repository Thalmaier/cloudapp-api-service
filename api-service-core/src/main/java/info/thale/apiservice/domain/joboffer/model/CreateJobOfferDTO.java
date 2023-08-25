package info.thale.apiservice.core.domain.joboffer.model;

import java.util.List;

import javax.money.MonetaryAmount;

public record CreateJobOfferDTO(
        String title,
        String description,
        List<String> tags,
        MonetaryAmount salary,
        List<Skill> skills
) {}
