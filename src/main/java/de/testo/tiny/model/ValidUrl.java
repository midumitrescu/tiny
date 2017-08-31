package de.testo.tiny.model;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotNull;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.net.MalformedURLException;
import java.net.URL;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, ANNOTATION_TYPE, PARAMETER})
@Retention(RUNTIME)
@Documented
@NotNull
@ReportAsSingleViolation
@Constraint(validatedBy = {ValidUrl.Validator.class})
public @interface ValidUrl {

    String[] acceptedTypes() default {};

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String message() default "{de.testo.tiny.model.Url.invalid}";

    class Validator implements ConstraintValidator<ValidUrl, String> {

        @Override
        public void initialize(ValidUrl constraintAnnotation) {

        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            try {
                new URL(value);
                return true;
            } catch (MalformedURLException e) {
                return false;
            }
        }
    }
}
