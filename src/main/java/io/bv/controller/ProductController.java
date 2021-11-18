package io.bv.controller;

import com.blibli.oss.backend.common.model.response.Response;
import io.bv.model.Product;
import io.bv.validation.group.Create;
import io.bv.validation.group.Update;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static com.blibli.oss.backend.common.helper.ResponseHelper.status;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@Slf4j
@RestController
@RequestMapping("/api/products")
public class ProductController {

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

}
