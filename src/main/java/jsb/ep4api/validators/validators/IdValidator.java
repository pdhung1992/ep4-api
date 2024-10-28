package jsb.ep4api.validators.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jsb.ep4api.validators.annotations.ValidId;

public class IdValidator implements ConstraintValidator<ValidId, Long> {
    @Override
    public boolean isValid(Long id, ConstraintValidatorContext constraintValidatorContext) {
        if (id == null || id <= 0) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Id is required.")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
