package net.rewerk.webstore.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.rewerk.webstore.model.dto.request.address.CreateDto;
import net.rewerk.webstore.model.dto.request.address.SearchDto;
import net.rewerk.webstore.model.dto.response.common.PaginatedPayloadResponseDto;
import net.rewerk.webstore.model.dto.response.common.SinglePayloadResponseDto;
import net.rewerk.webstore.model.entity.Address;
import net.rewerk.webstore.model.entity.User;
import net.rewerk.webstore.model.specification.AddressSpecification;
import net.rewerk.webstore.service.entity.AddressService;
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
@RequestMapping("/api/v1/addresses")
public class AddressesController {
    private final AddressService addressService;

    @GetMapping
    public ResponseEntity<PaginatedPayloadResponseDto<Address>> getAddresses(
            @Valid SearchDto searchDto,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        Page<Address> result = addressService.findAll(
                AddressSpecification.getSpecification((User) userDetails),
                RequestUtils.getSortAndPageRequest(searchDto)
        );
        return ResponseUtils.createPaginatedResponse(result);
    }

    @PostMapping
    public ResponseEntity<SinglePayloadResponseDto<Address>> createAddress(
            @Valid @RequestBody CreateDto createDto,
            @AuthenticationPrincipal UserDetails userDetails,
            UriComponentsBuilder uriBuilder
    ) {
        Address result = addressService.create(createDto, (User) userDetails);
        return ResponseEntity.created(uriBuilder.replacePath("/api/v1/addresses/{addressId}")
                        .build(Map.of("addressId", result.getId())))
                .body(SinglePayloadResponseDto.<Address>builder()
                        .code(HttpStatus.CREATED.value())
                        .message(HttpStatus.CREATED.getReasonPhrase())
                        .payload(result)
                        .build());
    }
}
