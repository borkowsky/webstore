package net.rewerk.webstore.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.rewerk.webstore.model.dto.request.brand.PatchDto;
import net.rewerk.webstore.model.dto.response.common.SinglePayloadResponseDto;
import net.rewerk.webstore.model.entity.Brand;
import net.rewerk.webstore.service.entity.BrandService;
import net.rewerk.webstore.util.ResponseUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/brands/{id:\\d+}")
@RestController
@RequiredArgsConstructor
public class BrandController {
    private final BrandService brandService;

    @ModelAttribute("brand")
    public Brand getBrand(@PathVariable Integer id) {
        return brandService.findById(id);
    }

    @GetMapping
    public ResponseEntity<SinglePayloadResponseDto<Brand>> findBrand(
            @ModelAttribute("brand") Brand brand
    ) {
        return ResponseUtils.createSingleResponse(brand);
    }

    @PatchMapping
    public ResponseEntity<Void> updateBrand(
            @ModelAttribute("brand") Brand brand,
            @Valid @RequestBody PatchDto request
    ) {
        brandService.update(brand, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteBrand(
            @ModelAttribute("brand") Brand brand
    ) {
        brandService.delete(brand);
        return ResponseEntity.noContent().build();
    }
}
