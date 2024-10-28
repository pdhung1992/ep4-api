package jsb.ep4api.validators.annotations;

import jakarta.validation.Constraint;
import jsb.ep4api.validators.validators.TokenValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TokenValidator.class)
public @interface ValidToken {
    String message() default "Invalid token";
    Class<?>[] groups() default {};
    Class<?>[] payload() default {};
}
