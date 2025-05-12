package net.rewerk.webstore.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.rewerk.webstore.transport.dto.request.category.PatchDto;
import net.rewerk.webstore.transport.dto.response.common.SinglePayloadResponseDto;
import net.rewerk.webstore.model.entity.Category;
import net.rewerk.webstore.service.entity.CategoryService;
import net.rewerk.webstore.util.ResponseUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/categories/{id:\\d+}")
@RequiredArgsConstructor
@RestController
class CategoryController {
    private final CategoryService categoryService;

    @ModelAttribute("category")
    public Category getCategory(@PathVariable Integer id) {
        return categoryService.findById(id);
    }

    @GetMapping
    public ResponseEntity<SinglePayloadResponseDto<Category>> findCategory(
            @ModelAttribute("category") Category category
    ) {
        return ResponseUtils.createSingleResponse(category);
    }

    @PatchMapping
    public ResponseEntity<Void> updateCategory(
            @ModelAttribute("category") Category currentCategory,
            @Valid @RequestBody PatchDto requestDto
    ) {
        categoryService.update(currentCategory, requestDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCategory(
            @ModelAttribute("category") Category category
    ) {
        categoryService.delete(category);
        return ResponseEntity.noContent().build();
    }
}
