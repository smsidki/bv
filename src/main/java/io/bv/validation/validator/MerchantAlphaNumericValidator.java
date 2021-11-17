package io.bv.validation.validator;

import io.bv.model.Merchant;
import io.bv.validation.constraint.AlphaNumeric;
import lombok.SneakyThrows;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.stream.Collectors;

public class MerchantAlphaNumericValidator implements ConstraintValidator<AlphaNumeric, Merchant> {

  @Override
  @SneakyThrows
  public boolean isValid(Merchant merchant, ConstraintValidatorContext context) {
    List<String> invalidFields = BeanUtils.describe(merchant)
      .values()
      .stream()
      .filter(field -> !StringUtils.isAlphanumeric(field))
      .collect(Collectors.toList());

    if (CollectionUtils.isNotEmpty(invalidFields)) {
      context
        .unwrap(HibernateConstraintValidatorContext.class)
        .addExpressionVariable("validatedValue", invalidFields);
      return false;
    }

    return true;
  }

}
