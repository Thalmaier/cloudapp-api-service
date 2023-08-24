package info.thale.apiservice.domain.joboffer.model;

public record Skill(
        String name,
        String description,
        SkillRating rating
) {}
