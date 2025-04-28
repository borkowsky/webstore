package net.rewerk.webstore.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.rewerk.webstore.model.dto.request.category.CreateDto;
import net.rewerk.webstore.model.dto.request.category.PatchDto;
import net.rewerk.webstore.model.dto.request.category.SearchDto;
import net.rewerk.webstore.model.dto.response.BaseResponseDto;
import net.rewerk.webstore.model.dto.response.common.PaginatedPayloadResponseDto;
import net.rewerk.webstore.model.dto.response.common.SinglePayloadResponseDto;
import net.rewerk.webstore.model.entity.Category;
import net.rewerk.webstore.model.specification.CategorySpecification;
import net.rewerk.webstore.service.entity.CategoryService;
import net.rewerk.webstore.util.RequestUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@RestController
class CategoryController {
    private final CategoryService categoryService;

    @GetMapping({"", "/"})
    public ResponseEntity<PaginatedPayloadResponseDto<Category>> index(
            @Valid SearchDto requestParams
            ) {
        Page<Category> categories = categoryService.findAll(
                CategorySpecification.getSpecification(requestParams),
                RequestUtils.getSortAndPageRequest(requestParams)
        );
        return ResponseEntity.ok(PaginatedPayloadResponseDto.<Category>builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .payload(categories.getContent())
                .page(categories.getNumber() + 1)
                .pages(categories.getTotalPages())
                .size(categories.getSize())
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SinglePayloadResponseDto<Category>> get(
            @PathVariable Integer id
    ) {
        Category category = categoryService.findById(id);
        HttpStatus status = category == null ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        SinglePayloadResponseDto<Category> response = SinglePayloadResponseDto.<Category>builder()
                .code(status.value())
                .message(status.getReasonPhrase())
                .payload(category)
                .build();
        return new ResponseEntity<>(response, status);
    }

    @PostMapping
    public SinglePayloadResponseDto<Category> save(
            @Valid @RequestBody CreateDto categoryDto
    ) {
        HttpStatus status = HttpStatus.CREATED;
        Category result = null;
        Category parentCategory = categoryDto.getCategory_id() == null ?
                null :
                categoryService.findById(categoryDto.getCategory_id());
        Category category = Category.builder()
                .name(categoryDto.getName())
                .description(categoryDto.getDescription())
                .category_id(parentCategory == null ? null : parentCategory.getId())
                .icon(categoryDto.getIcon())
                .enabled(true)
                .build();
        try {
            result = categoryService.create(category);
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return SinglePayloadResponseDto.<Category>builder()
                .code(status.value())
                .message(status.getReasonPhrase())
                .payload(result)
                .build();
    }

    @PatchMapping("/{id}")
    public SinglePayloadResponseDto<Category> update(
            @PathVariable("id") Integer id,
            @Valid @RequestBody PatchDto requestDto
    ) {
        HttpStatus status = HttpStatus.NO_CONTENT;
        Category result = categoryService.findById(id);
        if (result == null) {
            status = HttpStatus.NOT_FOUND;
        } else {
            if (requestDto.getCategory_id() != null) {
                Category category = categoryService.findById(requestDto.getCategory_id());
                if (category != null) {
                    result.setCategory_id(category.getId());
                }
            }
            if (requestDto.getName() != null) result.setName(requestDto.getName());
            if (requestDto.getDescription() != null) result.setDescription(requestDto.getDescription());
            if (requestDto.getIcon() != null) result.setIcon(requestDto.getIcon());
            if (requestDto.getEnabled() != null) result.setEnabled(requestDto.getEnabled());
            result = categoryService.update(result);
        }
        return SinglePayloadResponseDto.<Category>builder()
                .code(status.value())
                .message(status.getReasonPhrase())
                .payload(result)
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponseDto> delete(
            @PathVariable Integer id
    ) {
        HttpStatus status = HttpStatus.NO_CONTENT;
        Category category = categoryService.findById(id);
        if (category != null) {
            categoryService.delete(id);
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(
                BaseResponseDto.builder()
                        .code(status.value())
                        .message(status.getReasonPhrase())
                        .build(),
                status
        );
    }
}
