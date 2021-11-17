package io.bv.model;

import io.bv.validation.constraint.AlphaNumeric;
import lombok.Getter;

@Getter
public class Merchant {

  @AlphaNumeric
  public Merchant(String name, String code, int catalogLimit) {
    this.name = name;
    this.code = code;
    this.catalogLimit = catalogLimit;
  }

  private final String name;
  private final String code;
  private final int catalogLimit;

}
