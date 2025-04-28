package net.rewerk.webstore.model.dto.request.product;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatchDto {
    @Size(
            min = 4,
            max = 64,
            message = "Name {validation.common.length}"
    )
    private String name;
    @Size(
            min = 12,
            max = 4086,
            message = "Description {validation.common.length}"
    )
    private String description;
    private Integer category_id;
    @Positive(message = "Price {validation.common.positive}")
    private Double price;
    @Positive(message = "Discount price {validation.common.positive}")
    private Double discountPrice;
    private Integer balance;
    private String[] images;
    private Boolean enabled = Boolean.TRUE;
}
