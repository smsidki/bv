package io.bv.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.i18n.SimpleLocaleContext;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.adapter.HttpWebHandlerAdapter;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;
import org.springframework.web.server.i18n.LocaleContextResolver;

import java.util.Locale;
import java.util.Optional;

@Configuration
public class WebConfig {

  @Bean
  public HttpHandler httpHandler(ApplicationContext ctx) {
    // issue: https://github.com/spring-projects/spring-framework/issues/21764
    // workaround: https://github.com/spring-projects/spring-boot/issues/14253#issuecomment-417352385
    HttpHandler delegate = WebHttpHandlerBuilder.applicationContext(ctx).build();
    HttpWebHandlerAdapter httpWebHandlerAdapter = new HttpWebHandlerAdapter(((HttpWebHandlerAdapter) delegate)) {
      @Override
      public ServerWebExchange createExchange(ServerHttpRequest request, ServerHttpResponse response) {
        ServerWebExchange serverWebExchange = super.createExchange(request, response);
        LocaleContext localeContext = serverWebExchange.getLocaleContext();
        LocaleContextHolder.setLocaleContext(localeContext);
        return serverWebExchange;
      }
    };
    httpWebHandlerAdapter.setLocaleContextResolver(hybridLocaleContextResolver());
    return httpWebHandlerAdapter;
  }

  private LocaleContextResolver hybridLocaleContextResolver() {
    return new LocaleContextResolver() {
      @Override
      public LocaleContext resolveLocaleContext(ServerWebExchange exchange) {
        Locale locale = Optional
          .ofNullable(exchange.getRequest().getQueryParams().getFirst("hl"))
          .map(Optional::of)
          .orElseGet(() -> Optional.ofNullable(exchange.getRequest().getHeaders().getFirst("Accept-Language")))
          .map(Locale::forLanguageTag)
          .orElseGet(Locale::getDefault);
        return new SimpleLocaleContext(locale);
      }

      @Override
      public void setLocaleContext(ServerWebExchange exchange, LocaleContext localeContext) {
        throw new UnsupportedOperationException();
      }
    };
  }

}
