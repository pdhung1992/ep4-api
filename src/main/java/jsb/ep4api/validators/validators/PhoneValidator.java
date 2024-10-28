package jsb.ep4api.validators.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jsb.ep4api.validators.annotations.ValidPhone;

import static jsb.ep4api.constants.Constants.*;

public class PhoneValidator implements ConstraintValidator<ValidPhone, String> {
    @Override
    public boolean isValid(String phone, ConstraintValidatorContext context) {
        if (phone == null || phone.trim().isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Phone is required.")
                    .addConstraintViolation();
            return false;
        }
        if (!phone.matches(PHONE_PATTERN)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Phone must be 10 digits.")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
