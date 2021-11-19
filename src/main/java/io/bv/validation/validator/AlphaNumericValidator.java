package io.bv.validation.validator;

import io.bv.properties.ValidationProperties;
import io.bv.validation.constraint.AlphaNumeric;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static io.bv.properties.ValidationProperties.FieldProperties;

public class AlphaNumericValidator implements ConstraintValidator<AlphaNumeric, String> {

  private AlphaNumeric annotation;

  @Autowired
  private ValidationProperties props;

  @Override
  public void initialize(AlphaNumeric annotation) {
    this.annotation = annotation;
  }

  @Override
  public boolean isValid(String str, ConstraintValidatorContext context) {
    if (props.getFields().getOrDefault(annotation.name(), FieldProperties.newInstance()).isDisabled()) {
      return true;
    }
    return StringUtils.isAlphanumeric(str);
  }

}
