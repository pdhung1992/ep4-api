package jsb.ep4api.validators.annotations;

import jakarta.validation.Constraint;
import jsb.ep4api.validators.validators.SlugValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SlugValidator.class)
public @interface ValidSlug {
    String message() default "Invalid slug";
    Class<?>[] groups() default {};
    Class<?>[] payload() default {};
}
