package io.bv.validation.validator;

import io.bv.validation.constraint.EqualToString;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EqualToStringValidator implements ConstraintValidator<EqualToString, String> {

  private EqualToString annotation;

  public void initialize(EqualToString annotation) {
    this.annotation = annotation;
  }

  @Override
  public boolean isValid(String str, ConstraintValidatorContext context) {
    if (annotation.ignoreCase()) {
      return StringUtils.equalsAnyIgnoreCase(str, annotation.value());
    }
    return StringUtils.equalsAny(str, annotation.value());
  }

}
