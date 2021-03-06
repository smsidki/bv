package io.bv.model;

import io.bv.validation.constraint.AlphaNumeric;
import io.bv.validation.constraint.EqualToString;
import io.bv.validation.group.Create;
import io.bv.validation.group.Delete;
import io.bv.validation.group.Read;
import io.bv.validation.group.Update;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

  @NotBlank(
    groups = {
      Create.class,
      Read.class,
      Update.class,
      Delete.class
    }
  )
  private String code;

  private String name;

  @NotBlank
  @AlphaNumeric(
    groups = Create.class,
    name = "model-product-name"
  )
  private String brand;

  @EqualToString(
    ignoreCase = false,
    value = {"PRISTINE", "OFFICIAL", "OFFLINE"}
  )
  private String type;

  @EqualToString({"red", "green", "blue", "black", "white"})
  private String color;

  @PositiveOrZero
  private double rating;

  @Future(
    groups = {Create.class, Read.class},
    message = "product already expired: ${validatedValue}"
  )
  private Date expireDate;

  @Valid
  @NotEmpty
  private List<Item> items;

}