package net.rewerk.webstore.model.dto.request.basket;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreateDto {
    @NotNull(message = "Product ID {validation.common.required}")
    private Integer product_id;
    @NotNull(message = "Amount {validation.common.required}")
    @Positive(message = "Amount {validation.common.positive}")
    private Integer amount;
}
