package net.rewerk.webstore.controller;

import lombok.RequiredArgsConstructor;
import net.rewerk.webstore.transport.dto.request.order.PatchDto;
import net.rewerk.webstore.transport.dto.response.common.SinglePayloadResponseDto;
import net.rewerk.webstore.model.entity.Order;
import net.rewerk.webstore.model.entity.User;
import net.rewerk.webstore.service.entity.OrderService;
import net.rewerk.webstore.util.ResponseUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/orders/{id:\\d+}")
public class OrderController {
    private final OrderService orderService;

    @ModelAttribute("order")
    public Order getOrder(
            @PathVariable("id") Integer id,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return orderService.findByOrderIdAndUser(id, (User) userDetails);
    }

    @GetMapping
    public ResponseEntity<SinglePayloadResponseDto<Order>> getOrder(
            @ModelAttribute Order order
    ) {
        return ResponseUtils.createSingleResponse(order);
    }

    @PatchMapping
    public ResponseEntity<Void> updateOrder(
            @ModelAttribute("order") Order order,
            @RequestBody PatchDto patchDto
    ) {
        orderService.update(order, patchDto);
        return ResponseEntity.noContent().build();
    }
}
