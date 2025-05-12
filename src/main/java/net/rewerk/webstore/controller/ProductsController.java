package net.rewerk.webstore.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.rewerk.webstore.transport.dto.request.product.CreateDto;
import net.rewerk.webstore.transport.dto.request.product.SearchDto;
import net.rewerk.webstore.transport.dto.response.common.PaginatedPayloadResponseDto;
import net.rewerk.webstore.transport.dto.response.common.PayloadResponseDto;
import net.rewerk.webstore.transport.dto.response.common.SinglePayloadResponseDto;
import net.rewerk.webstore.model.entity.Brand;
import net.rewerk.webstore.model.entity.Category;
import net.rewerk.webstore.model.entity.Product;
import net.rewerk.webstore.transport.dto.mapper.ProductDtoMapper;
import net.rewerk.webstore.model.specification.ProductSpecification;
import net.rewerk.webstore.service.entity.BrandService;
import net.rewerk.webstore.service.entity.CategoryService;
import net.rewerk.webstore.service.entity.ProductService;
import net.rewerk.webstore.util.RequestUtils;
import net.rewerk.webstore.util.ResponseUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/products")
public class ProductsController {
    private final ProductDtoMapper productDtoMapper;
    private final ProductService productService;
    private final CategoryService categoryService;
    private final BrandService brandService;

    @GetMapping
    public ResponseEntity<PaginatedPayloadResponseDto<Product>> findAllProducts(
            @Valid SearchDto request
    ) {
        Page<Product> result = productService.findAll(
                ProductSpecification.getSpecification(request),
                RequestUtils.getSortAndPageRequest(request)
        );
        return ResponseUtils.createPaginatedResponse(result);
    }

    @GetMapping("/popular")
    public ResponseEntity<PayloadResponseDto<Product>> getPopularProducts() {
        List<Product> result = productService.findPopular();
        return ResponseUtils.createCollectionResponse(result);
    }

    @PostMapping
    public ResponseEntity<SinglePayloadResponseDto<Product>> createProduct(
            @Valid @RequestBody CreateDto request,
            UriComponentsBuilder uriBuilder
    ) {
        Product product = productDtoMapper.createProduct(request);
        Category category = categoryService.findById(request.getCategory_id());
        Brand brand = brandService.findById(request.getBrand_id());
        product.setCategory(category);
        product.setBrand(brand);
        Product result = productService.create(product);
        return ResponseEntity.created(uriBuilder
                        .replacePath("/api/v1/products/{productId}")
                        .build(Map.of("productId", product.getId())))
                .body(SinglePayloadResponseDto.<Product>builder()
                        .code(HttpStatus.CREATED.value())
                        .message(HttpStatus.CREATED.getReasonPhrase())
                        .payload(result)
                        .build());
    }
}
