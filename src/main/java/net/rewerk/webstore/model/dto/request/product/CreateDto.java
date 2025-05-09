package net.rewerk.webstore.model.dto.request.product;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateDto {
    @NotNull(message = "Name {validation.common.required}")
    @Size(
            min = 4,
            max = 64,
            message = "Name {validation.common.length}"
    )
    private String name;
    @NotNull(message = "Description {validation.common.required}")
    @Size(
            min = 12,
            max = 4086,
            message = "Description {validation.common.length}"
    )
    private String description;
    @NotNull(message = "Category ID {validation.common.required}")
    private Integer category_id;
    @NotNull(message = "Brand ID {validation.common.required}")
    private Integer brand_id;
    @NotNull(message = "Price {validation.common.required}")
    @Positive(message = "Price {validation.common.positive}")
    private Double price;
    @Positive(message = "Discount price {validation.common.positive}")
    private Double discountPrice;
    @NotNull(message = "Address {validation.common.required}")
    @Size(
            min = 6,
            max = 255,
            message = "Address {validation.common.range}"
    )
    private String address;
    @NotNull(message = "Images {validation.common.required}")
    private String[] images;
    private String[] tags;
    private Boolean enabled = Boolean.TRUE;
    @Setter(AccessLevel.NONE)
    private Double rating = 0.0d;
}
