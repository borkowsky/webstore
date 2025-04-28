package net.rewerk.webstore.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.rewerk.webstore.model.dto.request.product.CreateDto;
import net.rewerk.webstore.model.dto.request.product.PatchDto;
import net.rewerk.webstore.model.dto.request.product.SearchDto;
import net.rewerk.webstore.model.dto.response.common.PaginatedPayloadResponseDto;
import net.rewerk.webstore.model.dto.response.common.SinglePayloadResponseDto;
import net.rewerk.webstore.model.entity.Category;
import net.rewerk.webstore.model.entity.Product;
import net.rewerk.webstore.model.specification.ProductSpecification;
import net.rewerk.webstore.service.UploadService;
import net.rewerk.webstore.service.entity.CategoryService;
import net.rewerk.webstore.service.entity.ProductService;
import net.rewerk.webstore.util.CommonUtils;
import net.rewerk.webstore.util.RequestUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final UploadService uploadService;

    @GetMapping({"", "/"})
    public ResponseEntity<PaginatedPayloadResponseDto<Product>> index(
            @Valid SearchDto request
    ) {
        Page<Product> result = productService.findAll(
                ProductSpecification.getSpecification(request),
                RequestUtils.getSortAndPageRequest(request)
        );
        return ResponseEntity.ok(
                PaginatedPayloadResponseDto.<Product>builder()
                        .code(HttpStatus.OK.value())
                        .message(HttpStatus.OK.getReasonPhrase())
                        .payload(result.getContent())
                        .page(result.getNumber() + 1)
                        .pages(result.getTotalPages())
                        .size(result.getSize())
                        .build()
        );
    }

    @PostMapping()
    public ResponseEntity<SinglePayloadResponseDto<Product>> create(
            @Valid @RequestBody CreateDto request
    ) {
        HttpStatus status = HttpStatus.CREATED;
        String message = null;
        Product result = null;
        Category category = categoryService.findById(request.getCategory_id());
        if (category != null) {
            Product product = Product.builder()
                    .name(request.getName())
                    .description(request.getDescription())
                    .price(request.getPrice())
                    .category(category)
                    .discountPrice(request.getDiscountPrice())
                    .images(Arrays.stream(request.getImages()).toList())
                    .balance(request.getBalance())
                    .enabled(request.getEnabled())
                    .build();
            try {
                result = productService.create(product);
            } catch (Exception e) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        } else {
            status = HttpStatus.NOT_FOUND;
            message = "Category not found";
        }
        return new ResponseEntity<>(
                SinglePayloadResponseDto.<Product>builder()
                        .code(status.value())
                        .message(message == null ? status.getReasonPhrase() : message)
                        .payload(result)
                        .build(),
                status
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SinglePayloadResponseDto<Product>> update(
            @PathVariable Integer id,
            @Valid @RequestBody PatchDto request
            ) {
        HttpStatus status = HttpStatus.NO_CONTENT;
        String message = null;
        Product result = null;
        try {
            Product old = productService.findById(id);
            if (old == null) {
                message = "Product not found";
                status = HttpStatus.NOT_FOUND;
            } else {
                List<String> imagesToDelete = new ArrayList<>();
                if (request.getName() != null) {
                    old.setName(request.getName());
                }
                if (request.getDescription() != null ) {
                    old.setDescription(request.getDescription());
                }
                if (request.getPrice() != null) {
                    old.setPrice(request.getPrice());
                }
                if (request.getDiscountPrice() != null) {
                    old.setDiscountPrice(request.getDiscountPrice());
                }
                if (request.getImages() != null) {
                    imagesToDelete = CommonUtils.lostItemsInList(old.getImages(), Arrays.asList(request.getImages()));
                    old.setImages(Arrays.asList(request.getImages()));
                }
                if (request.getBalance() != null) {
                    old.setBalance(request.getBalance());
                }
                if (request.getEnabled() != null) {
                    old.setEnabled(request.getEnabled());
                }
                if (request.getCategory_id() != null) {
                    Category category = categoryService.findById(request.getCategory_id());
                    if (category != null) {
                        old.setCategory(category);
                    } else {
                        message = "Category not found";
                        status = HttpStatus.NOT_FOUND;
                    }
                }
                if (status == HttpStatus.NO_CONTENT) {
                    result = productService.update(old);
                    if (result != null && !imagesToDelete.isEmpty()) {
                        uploadService.deleteObjects(imagesToDelete).join();
                    }
                }
            }
        } catch (Exception e) {
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(
                SinglePayloadResponseDto.<Product>builder()
                        .code(status.value())
                        .message(message == null ? status.getReasonPhrase() : message)
                        .payload(result)
                        .build(),
                status
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SinglePayloadResponseDto<Product>> delete(
            @PathVariable Integer id
    ) {
        HttpStatus status = HttpStatus.NO_CONTENT;
        Product result = productService.findById(id);
        if (result == null) {
            status = HttpStatus.NOT_FOUND;
        } else {
            try {
                if (result.getImages() != null && !result.getImages().isEmpty()) {
                    uploadService.deleteObjects(result.getImages()).join();
                }
                productService.delete(id);
            } catch (InterruptedException e) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        }
        return new ResponseEntity<>(
                SinglePayloadResponseDto.<Product>builder()
                        .code(status.value())
                        .message(status.getReasonPhrase())
                        .payload(result)
                        .build(),
                status
        );
    }
}
