package net.rewerk.webstore.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.rewerk.webstore.transport.dto.request.product.PatchDto;
import net.rewerk.webstore.transport.dto.response.common.SinglePayloadResponseDto;
import net.rewerk.webstore.model.entity.Product;
import net.rewerk.webstore.service.entity.ProductService;
import net.rewerk.webstore.util.ResponseUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products/{id:\\d+}")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @ModelAttribute("product")
    public Product getProduct(@PathVariable Integer id) {
        return productService.findById(id);
    }

    @GetMapping
    public ResponseEntity<SinglePayloadResponseDto<Product>> findProduct(
            @ModelAttribute("product") Product product
    ) {
        return ResponseUtils.createSingleResponse(product);
    }

    @PatchMapping
    public ResponseEntity<Void> updateProduct(
            @ModelAttribute("product") Product currentProduct,
            @Valid @RequestBody PatchDto request
    ) {
        productService.update(currentProduct, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteProduct(
            @ModelAttribute("product") Product product
    ) {
        productService.delete(product);
        return ResponseEntity.noContent().build();
    }
}
