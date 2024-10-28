package jsb.ep4api.validators.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jsb.ep4api.validators.annotations.ValidCurrency;

public class CurrencyValidator implements ConstraintValidator<ValidCurrency, Double> {
    @Override
    public boolean isValid(Double currency, ConstraintValidatorContext constraintValidatorContext) {
        if (currency == null || currency < 0) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Currency is required.")
                    .addConstraintViolation();
            return false;
        }

        String currencyStr = currency.toString();
        if (currencyStr.contains(".")) {
            String[] parts = currencyStr.split("\\.");
            if (parts.length > 1 && parts[1].length() > 2) {
                constraintValidatorContext.disableDefaultConstraintViolation();
                constraintValidatorContext.buildConstraintViolationWithTemplate("Currency must have at most 2 decimal places.")
                        .addConstraintViolation();
                return false;
            }
        }
        return true;
    }
}
