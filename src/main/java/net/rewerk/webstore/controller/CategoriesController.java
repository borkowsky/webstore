package net.rewerk.webstore.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.rewerk.webstore.transport.dto.request.category.CreateDto;
import net.rewerk.webstore.transport.dto.request.category.SearchDto;
import net.rewerk.webstore.transport.dto.response.common.PaginatedPayloadResponseDto;
import net.rewerk.webstore.transport.dto.response.common.SinglePayloadResponseDto;
import net.rewerk.webstore.model.entity.Category;
import net.rewerk.webstore.model.specification.CategorySpecification;
import net.rewerk.webstore.service.entity.CategoryService;
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
@RequestMapping("/api/v1/categories")
public class CategoriesController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<PaginatedPayloadResponseDto<Category>> findAllCategories(
            @Valid SearchDto requestParams
    ) {
        Page<Category> categories = categoryService.findAll(
                CategorySpecification.getSpecification(requestParams),
                RequestUtils.getSortAndPageRequest(requestParams)
        );
        return ResponseUtils.createPaginatedResponse(categories);
    }

    @PostMapping
    public ResponseEntity<SinglePayloadResponseDto<Category>> createCategory(
            @Valid @RequestBody CreateDto request,
            UriComponentsBuilder uriBuilder
    ) {
        Category result = categoryService.create(request);
        return ResponseEntity.created(uriBuilder
                        .replacePath("/api/v1/categories/{categoryId}")
                        .build(Map.of("categoryId", result.getId()))
                )
                .body(SinglePayloadResponseDto.<Category>builder()
                        .code(HttpStatus.CREATED.value())
                        .message(HttpStatus.CREATED.getReasonPhrase())
                        .payload(result)
                        .build());
    }
}
