package jsb.ep4api.validators.validators;

import jakarta.validation.ConstraintValidator;
import jsb.ep4api.validators.annotations.ValidSlug;

import static jsb.ep4api.constants.Constants.SLUG_PATTERN;

public class SlugValidator implements ConstraintValidator<ValidSlug, String> {
    @Override
    public boolean isValid(String slug, jakarta.validation.ConstraintValidatorContext constraintValidatorContext) {
        if (slug == null || slug.trim().isEmpty()) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Slug is required.")
                    .addConstraintViolation();
            return false;
        }
        if (slug.length() < 5 || slug.length() > 50) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Slug must be between 5 and 50 characters.")
                    .addConstraintViolation();
            return false;
        }
        if (!slug.matches(SLUG_PATTERN)) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Slug must contain only lowercase letters, numbers, and hyphens.")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
