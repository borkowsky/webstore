package net.rewerk.webstore.transport.dto.request.order;

import lombok.Data;
import net.rewerk.webstore.model.entity.Order;
import net.rewerk.webstore.model.entity.Payment;

@Data
public class PatchDto {
    private Order.Status order_status;
    private Payment.Status payment_status;
}
