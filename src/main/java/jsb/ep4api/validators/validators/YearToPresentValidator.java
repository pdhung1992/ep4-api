package jsb.ep4api.validators.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jsb.ep4api.validators.annotations.ValidYearToPresent;

import java.time.Year;

public class YearToPresentValidator implements ConstraintValidator<ValidYearToPresent, Integer> {
    @Override
    public boolean isValid(Integer year, ConstraintValidatorContext constraintValidatorContext) {
        if (year == null) {
            return true;
        }
        if (year < 1900 || year > Year.now().getValue()) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Invalid year.")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
