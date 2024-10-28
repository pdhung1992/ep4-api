package jsb.ep4api.validators.validators;


import jakarta.validation.ConstraintValidator;
import jsb.ep4api.validators.annotations.ValidFullName;

public class FullNameValidator implements ConstraintValidator<ValidFullName, String> {
    @Override
    public boolean isValid(String fullName, jakarta.validation.ConstraintValidatorContext constraintValidatorContext) {
        if (fullName == null || fullName.trim().isEmpty()) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Full name is required.")
                    .addConstraintViolation();
            return false;
        }
        if (fullName.length() < 5 || fullName.length() > 50) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Full name must be between 5 and 50 characters.")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
