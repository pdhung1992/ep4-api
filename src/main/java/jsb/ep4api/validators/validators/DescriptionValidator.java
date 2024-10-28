package jsb.ep4api.validators.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jsb.ep4api.validators.annotations.ValidDescription;

public class DescriptionValidator implements ConstraintValidator<ValidDescription, String> {
    @Override
    public boolean isValid(String description, ConstraintValidatorContext constraintValidatorContext) {
        if (description == null || description.trim().isEmpty()) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Description is required.")
                    .addConstraintViolation();
            return false;
        }
        if (description.length() < 10 ) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Description must be at least 10 characters.")
                    .addConstraintViolation();
            return false;
        }
        if (description.length() > 1000) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Description must be at most 1000 characters.")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
