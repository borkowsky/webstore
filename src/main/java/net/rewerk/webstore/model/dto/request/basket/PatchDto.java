package net.rewerk.webstore.model.dto.request.basket;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PatchDto {
    @Positive(message = "Amount {validation.common.positive}")
    private Integer amount;
}
