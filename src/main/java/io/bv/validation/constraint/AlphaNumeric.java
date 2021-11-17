package io.bv.validation.constraint;

import io.bv.validation.validator.AlphaNumericValidator;
import io.bv.validation.validator.MerchantAlphaNumericValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Retention(RUNTIME)
@Target({FIELD, PARAMETER, CONSTRUCTOR, METHOD})
@Constraint(validatedBy = {AlphaNumericValidator.class, MerchantAlphaNumericValidator.class})
public @interface AlphaNumeric {

  String message() default "{io.bv.validation.constraint.AlphaNumeric.message}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

}
