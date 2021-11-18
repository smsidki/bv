package io.bv.controller;

import com.blibli.oss.backend.common.model.response.Response;
import io.bv.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

import static com.blibli.oss.backend.common.helper.ResponseHelper.status;
import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@RestController
@RequestMapping("/api/products")
public class ProductController {

  @PostMapping
  @ResponseStatus(CREATED)
  public Mono<Response<Void>> create(@RequestBody @Valid Product product) {
    log.info("Product {} created", product.getName());
    return Mono.just(status(CREATED));
  }

}
