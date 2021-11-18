package io.bv.model;

import io.bv.validation.group.Create;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {

  @NotBlank(groups = Create.class)
  private String sku;

}
