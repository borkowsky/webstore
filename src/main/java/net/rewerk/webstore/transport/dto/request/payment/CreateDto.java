package net.rewerk.webstore.transport.dto.request.payment;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreateDto {
    @NotNull(message = "Payment ID {validation.common.required}")
    @Positive(message = "Payment ID {validation.common.positive}")
    private Integer payment_id;
}
