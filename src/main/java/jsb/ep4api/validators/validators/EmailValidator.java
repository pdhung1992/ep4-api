package jsb.ep4api.validators.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jsb.ep4api.validators.annotations.ValidEmail;

import static jsb.ep4api.constants.Constants.EMAIL_PATTERN;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {
    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null || email.trim().isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Email is required.")
                    .addConstraintViolation();
            return false;
        }
        if (!email.matches(EMAIL_PATTERN)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Invalid email format.")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
