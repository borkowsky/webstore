package net.rewerk.webstore.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.rewerk.webstore.transport.dto.request.brand.CreateDto;
import net.rewerk.webstore.transport.dto.request.brand.SearchDto;
import net.rewerk.webstore.transport.dto.response.common.PaginatedPayloadResponseDto;
import net.rewerk.webstore.transport.dto.response.common.SinglePayloadResponseDto;
import net.rewerk.webstore.model.entity.Brand;
import net.rewerk.webstore.model.specification.BrandSpecification;
import net.rewerk.webstore.service.entity.BrandService;
import net.rewerk.webstore.util.RequestUtils;
import net.rewerk.webstore.util.ResponseUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/brands")
public class BrandsController {
    private final BrandService brandService;

    @GetMapping
    public ResponseEntity<PaginatedPayloadResponseDto<Brand>> findAllBrands(
            @Valid SearchDto request
    ) {
        Page<Brand> result = request.getProduct_category_id() != null ?
                brandService.findAllByProductCategoryId(request.getProduct_category_id())
                : brandService.findAll(
                BrandSpecification.getSpecification(request),
                RequestUtils.getSortAndPageRequest(request)
        );
        return ResponseUtils.createPaginatedResponse(result);
    }

    @PostMapping
    public ResponseEntity<SinglePayloadResponseDto<Brand>> createBrand(
            @Valid @RequestBody CreateDto request,
            UriComponentsBuilder uriBuilder
    ) {
        Brand result = brandService.create(request);
        return ResponseEntity.created(uriBuilder
                        .replacePath("/api/v1/brands/{brandId}")
                        .build(Map.of("brandId", result.getId())))
                .body(SinglePayloadResponseDto.<Brand>builder()
                        .code(HttpStatus.CREATED.value())
                        .message(HttpStatus.CREATED.getReasonPhrase())
                        .payload(result)
                        .build());
    }
}
