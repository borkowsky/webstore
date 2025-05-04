package net.rewerk.webstore.model.dto.request.favorite;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreateDto {
    @NotNull(message = "Product ID {validation.common.required}")
    @Positive(message = "Product id {validatio.common.positive}")
    private Integer product_id;
}
