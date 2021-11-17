package io.bv.validation.validator;

import io.bv.validation.constraint.AlphaNumeric;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AlphaNumericValidator implements ConstraintValidator<AlphaNumeric, String> {

  @Override
  public boolean isValid(String str, ConstraintValidatorContext context) {
    return StringUtils.isAlphanumeric(str);
  }

}
