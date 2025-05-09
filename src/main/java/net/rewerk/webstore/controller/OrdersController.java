package net.rewerk.webstore.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.rewerk.webstore.model.dto.request.order.CreateDto;
import net.rewerk.webstore.model.dto.request.order.SearchDto;
import net.rewerk.webstore.model.dto.response.common.PaginatedPayloadResponseDto;
import net.rewerk.webstore.model.dto.response.common.SinglePayloadResponseDto;
import net.rewerk.webstore.model.dto.response.order.CountersDto;
import net.rewerk.webstore.model.entity.Order;
import net.rewerk.webstore.model.entity.User;
import net.rewerk.webstore.model.specification.OrderSpecification;
import net.rewerk.webstore.service.entity.OrderService;
import net.rewerk.webstore.util.RequestUtils;
import net.rewerk.webstore.util.ResponseUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/orders")
public class OrdersController {
    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<PaginatedPayloadResponseDto<Order>> getAllOrders(
            @Valid SearchDto searchDto,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        Page<Order> result = orderService.findAll(
                OrderSpecification.getSpecification((User) userDetails, searchDto),
                RequestUtils.getSortAndPageRequest(searchDto)
        );
        return ResponseUtils.createPaginatedResponse(result);
    }

    @GetMapping("/counters")
    public ResponseEntity<SinglePayloadResponseDto<CountersDto>> getAllOrdersCounters(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        CountersDto result = orderService.getCounters((User) userDetails);
        return ResponseUtils.createSingleResponse(result);
    }

    @PostMapping
    public ResponseEntity<SinglePayloadResponseDto<Order>> createOrder(
            @Valid @RequestBody CreateDto createDto,
            @AuthenticationPrincipal UserDetails userDetails,
            UriComponentsBuilder uriBuilder
    ) {
        Order result = orderService.create(createDto, (User) userDetails);
        return ResponseEntity.created(uriBuilder.replacePath("/api/v1/orders/{orderId}")
                        .build(Map.of("orderId", result.getId())))
                .body(SinglePayloadResponseDto.<Order>builder()
                        .code(HttpStatus.CREATED.value())
                        .message(HttpStatus.CREATED.getReasonPhrase())
                        .payload(result)
                        .build());
    }
}
