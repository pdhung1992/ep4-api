package jsb.ep4api.validators.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jsb.ep4api.validators.annotations.ValidPassword;

import static jsb.ep4api.constants.Constants.PASSWORD_PATTERN;

public class PaswordValidator implements ConstraintValidator<ValidPassword, String> {
    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        if (password == null || password.trim().isEmpty()) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Password is required.")
                    .addConstraintViolation();
            return false;
        }
        if (password.length() < 6 || password.length() > 50) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Password must be between 6 and 50 characters.")
                    .addConstraintViolation();
            return false;
        }
        if (!password.matches(PASSWORD_PATTERN)) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Password must contain at least one digit, one lowercase and one uppercase character.")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
