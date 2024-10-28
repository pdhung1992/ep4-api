package jsb.ep4api.validators.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jsb.ep4api.validators.annotations.ValidRating;

public class RatingValidator implements ConstraintValidator<ValidRating, Integer> {
    @Override
    public boolean isValid(Integer rating, ConstraintValidatorContext constraintValidatorContext) {
        if (rating < 1 || rating > 5) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Rating must be between 1 and 5.")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
