package io.bv.controller;

import com.blibli.oss.backend.common.model.response.Response;
import io.bv.model.Product;
import io.bv.validation.group.Create;
import io.bv.validation.group.Update;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.blibli.oss.backend.common.helper.ResponseHelper.status;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RestController
@RequestMapping("/api/products")
public class ProductController {

  @Autowired
  private Validator validator;

  @Autowired
  private MessageSource messageSource;

  @PostMapping
  @ResponseStatus(CREATED)
  public Mono<Response<Void>> create(@RequestBody @Validated(Create.class) Product product) {
    log.info("Product {} created", product.getName());
    return Mono.just(status(CREATED));
  }

  @PutMapping
  @ResponseStatus(NO_CONTENT)
  public Mono<Response<Void>> update(@RequestBody @Validated(Update.class) Product product) {
    log.info("Product {} updated", product.getName());
    return Mono.just(status(NO_CONTENT));
  }

  @PostMapping("/_create_bulk")
  public Mono<Response<Pair<String, Integer>>> createBulk(@RequestBody List<Product> products, Locale locale) {
    return Mono.fromCallable(() -> {
      List<Product> validProducts = new ArrayList<>();
      Map<String, List<String>> errorProducts = new HashMap<>();
      products.forEach(product -> {
        Set<ConstraintViolation<Product>> violations = validator.validate(product, Create.class);
        if (CollectionUtils.isEmpty(violations)) {
          validProducts.add(product);
        } else {
          errorProducts.put(
            product.getCode(),
            violations
              .stream()
              .map(violation -> messageSource.getMessage(
                "field.invalid",
                new Object[]{violation.getPropertyPath().toString(), violation.getMessage()}, "field.invalid", locale
              ))
              .collect(Collectors.toList())
          );
        }
      });
      if (CollectionUtils.isNotEmpty(validProducts)) {
        log.info("{} product(s) created", validProducts.size());
      }
      return status(OK, Pair.of("created", validProducts.size()), null, errorProducts);
    });
  }

}
