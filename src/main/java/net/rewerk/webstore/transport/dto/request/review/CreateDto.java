package net.rewerk.webstore.transport.dto.request.review;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class CreateDto {
    @NotNull(message = "Order ID {validation.common.required}")
    @Positive(message = "Order ID {validation.common.positive}")
    private Integer order_id;
    @NotNull(message = "Product ID {validation.common.required}")
    @Positive(message = "Product ID {validation.common.positive}")
    private Integer product_id;
    @NotNull(message = "Text {validation.common.required}")
    @Size(
            min = 6,
            max = 2048,
            message = "Text {validation.common.length}"
    )
    private String text;
    @NotNull(message = "Rating {validation.message.required}")
    @Min(1)
    @Max(5)
    private Integer rating;
    @NotNull(message = "Images {validation.common.required}")
    @Size(
            max = 5,
            message = "Images {validation.common.range}"
    )
    private List<String> images;
}
