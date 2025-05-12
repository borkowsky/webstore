package net.rewerk.webstore.transport.dto.request.review;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProbeRequestDto {
    @NotNull(message = "Order ID {validation.common.required}")
    @Positive(message = "Order ID {validation.common.positive}")
    private Integer order_id;
    @NotNull(message = "Product ID {validation.common.required}")
    @Positive(message = "Product ID {validation.common.positive}")
    private Integer product_id;
}
