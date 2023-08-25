package info.thale.apiservice.core.domain.joboffer.model;

public record Skill(
        String name,
        String description,
        SkillRating rating
) {}
