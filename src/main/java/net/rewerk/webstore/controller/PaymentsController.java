package net.rewerk.webstore.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.rewerk.webstore.transport.dto.request.payment.CreateDto;
import net.rewerk.webstore.transport.dto.request.payment.SearchDto;
import net.rewerk.webstore.transport.dto.response.common.PaginatedPayloadResponseDto;
import net.rewerk.webstore.transport.dto.response.common.SinglePayloadResponseDto;
import net.rewerk.webstore.model.entity.Payment;
import net.rewerk.webstore.model.entity.User;
import net.rewerk.webstore.model.specification.PaymentSpecification;
import net.rewerk.webstore.service.entity.PaymentService;
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
@RequestMapping("/api/v1/payments")
public class PaymentsController {
    private final PaymentService paymentService;

    @GetMapping
    public ResponseEntity<PaginatedPayloadResponseDto<Payment>> getAllPayments(
            @Valid SearchDto searchDto,
            @AuthenticationPrincipal UserDetails userDetails) {
        Page<Payment> result = paymentService.findAll(
                PaymentSpecification.getSpecification(searchDto, (User) userDetails),
                RequestUtils.getSortAndPageRequest(searchDto)
        );
        return ResponseUtils.createPaginatedResponse(result);
    }

    @PostMapping
    public ResponseEntity<SinglePayloadResponseDto<Payment>> createPayment(
            @Valid @RequestBody CreateDto createDto,
            @AuthenticationPrincipal UserDetails userDetails,
            UriComponentsBuilder uriBuilder
    ) {
        Payment payment = paymentService.setAsPaid(createDto.getPayment_id(), (User) userDetails);
        return ResponseEntity.created(uriBuilder
                .replacePath("/api/v1/payments/{paymentId}")
                .build(Map.of("paymentId", payment.getId()))
        ).body(SinglePayloadResponseDto.<Payment>builder()
                .code(HttpStatus.CREATED.value())
                .message(HttpStatus.CREATED.getReasonPhrase())
                .payload(payment)
                .build());
    }
}
