package net.rewerk.webstore.model.dto.request.basket;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MultipleDeleteDto {
    @NotNull(message = "Basket IDS {validation.common.required}")
    @NotEmpty(message = "Basket IDS {validation.common.empty}")
    private Integer[] basket_ids;
}
