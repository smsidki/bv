package io.bv;

import io.bv.model.Merchant;
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

  public static void main(String[] args) throws NoSuchMethodException {
    Validator validator = Validation
      .byProvider(HibernateValidator.class)
      .configure()
      .messageInterpolator(
        new ResourceBundleMessageInterpolator(new PlatformResourceBundleLocator("message/validation/validation"))
      )
      .buildValidatorFactory()
      .getValidator();

    Product product = Product.builder()
      .brand("©inga")
      .type("PRISTINE")
      .color("yellow")
      .expireDate(Date.from(Instant.now().plus(1, ChronoUnit.DAYS)))
      .build();

    List<String> errors = validator
      .validate(product)
      .stream()
      .map(ConstraintViolation::getMessage)
      .collect(Collectors.toList());
    System.out.println(errors);

    final String merchantName = "©at";
    errors = validator
      .validate(new Merchant(merchantName, "cat", 100))
      .stream()
      .map(ConstraintViolation::getMessage)
      .collect(Collectors.toList());
    System.out.println(errors);

    errors = validator
      .forExecutables()
      .validateConstructorReturnValue(
        Merchant.class.getConstructor(String.class, String.class, int.class),
        new Merchant(merchantName, "cat", 100)
      )
      .stream()
      .map(ConstraintViolation::getMessage)
      .collect(Collectors.toList());
    System.out.println(errors);
  }

}
