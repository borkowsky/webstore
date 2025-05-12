package net.rewerk.webstore.transport.dto.request.order;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateDto {
    @NotNull(message = "Basket IDs {validation.common.required}")
    @NotEmpty(message = "Basket IDs {validation.common.empty}")
    private Integer[] basket_ids;
    @NotNull(message = "Address ID {validation.common.required}")
    private Integer address_id;
}
