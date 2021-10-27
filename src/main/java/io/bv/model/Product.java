package io.bv.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

  @NotBlank
  private String brand;

  @PositiveOrZero
  private double rating;

  @Future(message = "product already expired: ${validatedValue}")
  private Date expireDate;

}