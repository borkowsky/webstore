package net.rewerk.webstore.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.rewerk.webstore.model.dto.request.brand.CreateDto;
import net.rewerk.webstore.model.dto.request.brand.PatchDto;
import net.rewerk.webstore.model.dto.request.brand.SearchDto;
import net.rewerk.webstore.model.dto.response.common.PaginatedPayloadResponseDto;
import net.rewerk.webstore.model.dto.response.common.SinglePayloadResponseDto;
import net.rewerk.webstore.model.entity.Brand;
import net.rewerk.webstore.model.entity.Upload;
import net.rewerk.webstore.model.specification.BrandSpecification;
import net.rewerk.webstore.service.UploadService;
import net.rewerk.webstore.service.entity.BrandService;
import net.rewerk.webstore.util.RequestUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/brands")
@RestController
@RequiredArgsConstructor
public class BrandController {
    private final BrandService brandService;
    private final UploadService uploadService;

    @GetMapping({"", "/"})
    public ResponseEntity<PaginatedPayloadResponseDto<Brand>> index(
            @Valid SearchDto request
    ) {
        Page<Brand> result = brandService.findAll(
                BrandSpecification.getSpecification(request),
                RequestUtils.getSortAndPageRequest(request)
        );
        return ResponseEntity.ok(
                PaginatedPayloadResponseDto.<Brand>builder()
                        .code(HttpStatus.OK.value())
                        .message(HttpStatus.OK.getReasonPhrase())
                        .payload(result.getContent())
                        .page(result.getNumber() + 1)
                        .pages(result.getTotalPages() + 1)
                        .total(result.getTotalElements())
                        .build()
        );
    }

    @PostMapping({"", "/"})
    public ResponseEntity<SinglePayloadResponseDto<Brand>> create(
            @Valid @RequestBody CreateDto request) {
        HttpStatus status = HttpStatus.CREATED;
        String message = null;
        Brand result = null;
        try {
            result = brandService.create(Brand.builder()
                    .name(request.getName())
                    .image(request.getImage())
                    .build());
        } catch (Exception e) {
            status = HttpStatus.BAD_REQUEST;
            message = e.getMessage();
        }
        return new ResponseEntity<>(
                SinglePayloadResponseDto.<Brand>builder()
                        .code(status.value())
                        .message(message == null ? status.getReasonPhrase() : message)
                        .payload(result)
                        .build(),
                status
        );
    }

    @PatchMapping("/{id:\\d+}")
    public ResponseEntity<SinglePayloadResponseDto<Brand>> update(
            @PathVariable Integer id,
            @Valid @RequestBody PatchDto request
            ) {
        HttpStatus status = HttpStatus.NO_CONTENT;
        Brand brand = brandService.getById(id);
        if (brand == null) {
            status = HttpStatus.NOT_FOUND;
        } else {
            if (request.getName() != null) {
                brand.setName(request.getName());
            }
            if (request.getImage() != null) {
                if (!request.getImage().equals(brand.getImage())) {
                    try {
                        uploadService.deleteObject(brand.getImage(), Upload.Type.BRAND_IMAGE);
                    } catch (Exception e) {
                        // todo: implement logging
                    }
                }
                brand.setImage(request.getImage());
            }
            brand = brandService.update(brand);
        }
        return new ResponseEntity<>(
                SinglePayloadResponseDto.<Brand>builder()
                        .code(status.value())
                        .message(status.getReasonPhrase())
                        .payload(brand)
                        .build(),
                status
        );
    }

    @DeleteMapping("/{id:\\d+}")
    public ResponseEntity<SinglePayloadResponseDto<Brand>> delete(@PathVariable Integer id) {
        HttpStatus status = HttpStatus.NO_CONTENT;
        Brand brand = brandService.getById(id);
        if (brand == null) {
            status = HttpStatus.NOT_FOUND;
        } else {
            try {
                if (brand.getImage() != null) {
                    uploadService.deleteObject(brand.getImage(), Upload.Type.BRAND_IMAGE);
                }
                brandService.delete(id);
            } catch (Exception e) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        }
        return new ResponseEntity<>(
                SinglePayloadResponseDto.<Brand>builder()
                        .code(status.value())
                        .message(status.getReasonPhrase())
                        .payload(brand)
                        .build(),
                status
        );
    }
}
