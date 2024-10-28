package jsb.ep4api.validators.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jsb.ep4api.validators.annotations.ValidEntityName;

public class EntityNameValidator implements ConstraintValidator<ValidEntityName, String> {
    @Override
    public boolean isValid(String entityName, ConstraintValidatorContext constraintValidatorContext) {
        if (entityName == null || entityName.trim().isEmpty()) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Entity name is required.")
                    .addConstraintViolation();
            return false;
        }
        if (entityName.length() < 5 || entityName.length() > 50) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Entity name must be between 3 and 50 characters.")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
