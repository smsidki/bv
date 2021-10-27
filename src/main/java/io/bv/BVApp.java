package io.bv;

import io.bv.model.Product;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.resourceloading.PlatformResourceBundleLocator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class BVApp {

  static {
    Locale.setDefault(Locale.GERMANY);
  }

  public static void main(String[] args) {
    Validator validator = Validation
      .byProvider(HibernateValidator.class)
      .configure()
      .messageInterpolator(
        new ResourceBundleMessageInterpolator(new PlatformResourceBundleLocator("message/validation/validation"))
      )
      .buildValidatorFactory()
      .getValidator();

    Product product = Product.builder()
      .brand("")
      .expireDate(Date.from(Instant.now().minus(1, ChronoUnit.DAYS)))
      .build();

    List<String> errors = validator
      .validate(product)
      .stream()
      .map(ConstraintViolation::getMessage)
      .collect(Collectors.toList());

    System.out.println(errors);
    // [product already expired: ${validatedValue}, must not be blank]
  }

}
