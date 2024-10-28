package jsb.ep4api.validators.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jsb.ep4api.validators.annotations.ValidToken;

public class TokenValidator implements ConstraintValidator<ValidToken, String> {
    @Override
    public boolean isValid(String token, ConstraintValidatorContext constraintValidatorContext) {
        if (token == null || token.trim().isEmpty()) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Token is required.")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
