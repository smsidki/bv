package io.bv.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@Data
@ConfigurationProperties(prefix = "app.config.validation")
public class ValidationProperties {

  private Map<String, FieldProperties> fields;

  @Data
  public static class FieldProperties {

    private boolean disabled;

    public static FieldProperties newInstance() {
      return new FieldProperties();
    }

  }

}
