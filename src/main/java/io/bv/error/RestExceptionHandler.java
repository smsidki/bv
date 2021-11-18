package io.bv.error;

import com.blibli.oss.backend.common.model.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import static com.blibli.oss.backend.common.helper.ResponseHelper.badRequest;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

  @Autowired
  private MessageSource messageSource;

  @ExceptionHandler(WebExchangeBindException.class)
  public Mono<Response<?>> handleWebExchangeBindException(WebExchangeBindException ex, Locale locale) {
    Map<String, List<String>> errors = new HashMap<>();
    errors.put("messages", ex
      .getFieldErrors()
      .stream()
      .map(fieldError -> messageSource.getMessage(
        "field.invalid", new Object[]{fieldError.getField(), fieldError.getDefaultMessage()}, "field.invalid", locale
      ))
      .collect(Collectors.toList())
    );
    return Mono.just(badRequest(errors));
  }

}
