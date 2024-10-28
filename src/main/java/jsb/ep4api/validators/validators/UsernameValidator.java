package jsb.ep4api.validators.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jsb.ep4api.validators.annotations.ValidUsername;

public class UsernameValidator implements ConstraintValidator<ValidUsername, String> {
    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        if (username == null || username.trim().isEmpty()) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Username is required.")
                    .addConstraintViolation();
            return false;
        }
        if (username.length() < 5 || username.length() > 20) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Username must be between 5 and 20 characters.")
                    .addConstraintViolation();
            return false;
        }
        if (!username.matches("^[a-zA-Z0-9]*$")) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Username must not contain spaces or special characters.")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
