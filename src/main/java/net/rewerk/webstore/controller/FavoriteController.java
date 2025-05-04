package net.rewerk.webstore.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.rewerk.webstore.model.dto.request.favorite.CreateDto;
import net.rewerk.webstore.model.dto.request.favorite.SearchDto;
import net.rewerk.webstore.model.dto.response.BaseResponseDto;
import net.rewerk.webstore.model.dto.response.common.PaginatedPayloadResponseDto;
import net.rewerk.webstore.model.dto.response.common.PayloadResponseDto;
import net.rewerk.webstore.model.dto.response.common.SinglePayloadResponseDto;
import net.rewerk.webstore.model.entity.Category;
import net.rewerk.webstore.model.entity.Favorite;
import net.rewerk.webstore.model.entity.Product;
import net.rewerk.webstore.model.entity.User;
import net.rewerk.webstore.model.specification.FavoriteSpecification;
import net.rewerk.webstore.service.entity.CategoryService;
import net.rewerk.webstore.service.entity.FavoriteService;
import net.rewerk.webstore.service.entity.ProductService;
import net.rewerk.webstore.util.RequestUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RequestMapping("/api/v1/favorites")
@RestController
@RequiredArgsConstructor
public class FavoriteController {
    private final FavoriteService favoriteService;
    private final CategoryService categoryService;
    private final ProductService productService;

    @GetMapping({"", "/"})
    public ResponseEntity<PaginatedPayloadResponseDto<Favorite>> getFavorites(
            @Valid SearchDto request,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();
        Page<Favorite> result = favoriteService.findAll(
                FavoriteSpecification.getSpecification(user.getId(), request),
                RequestUtils.getSortAndPageRequest(request)
        );
        return ResponseEntity.ok(
                PaginatedPayloadResponseDto.<Favorite>builder()
                        .code(HttpStatus.OK.value())
                        .message(HttpStatus.OK.getReasonPhrase())
                        .payload(result.getContent())
                        .page(result.getNumber() + 1)
                        .pages(result.getTotalPages() + 1)
                        .total(result.getTotalElements())
                        .build()
        );
    }

    @GetMapping("/categories")
    public ResponseEntity<PayloadResponseDto<Category>> getFavoriteCategories(
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();
        List<Integer> categoriesIds = favoriteService.findCategoriesIds(
                FavoriteSpecification.getCategoriesSpecification(user.getId())
        );
        List<Category> categories = categoryService.findAllDistinctById(categoriesIds);
        return ResponseEntity.ok(
                PayloadResponseDto.<Category>builder()
                        .code(HttpStatus.OK.value())
                        .message(HttpStatus.OK.getReasonPhrase())
                        .payload(categories)
                        .build()
        );
    }

    @PostMapping({"", "/"})
    public ResponseEntity<SinglePayloadResponseDto<Favorite>> createFavorite(
            @Valid @RequestBody CreateDto request,
            Authentication authentication
            ) {
        User user = (User) authentication.getPrincipal();
        HttpStatus status = HttpStatus.CREATED;
        String message = null;
        Favorite result = null;
        Product product = productService.findById(request.getProduct_id());
        if (product == null) {
            status = HttpStatus.NOT_FOUND;
            message = "Product not found";
        } else {
            try {
                result = favoriteService.create(
                        Favorite.builder()
                                .user_id(user.getId())
                                .product(product)
                                .build()
                );
            } catch (Exception e) {
                status = HttpStatus.CONFLICT;
            }
        }
        return new ResponseEntity<>(
                SinglePayloadResponseDto.<Favorite>builder()
                        .code(status.value())
                        .message(message == null ? status.getReasonPhrase() : message)
                        .payload(result)
                        .build(),
                status
        );
    }

    @DeleteMapping("/{id:\\d+}")
    public ResponseEntity<BaseResponseDto> deleteFavorite(
            @PathVariable Integer id,
            Authentication authentication
    ) {
        HttpStatus status = HttpStatus.NO_CONTENT;
        User user = (User) authentication.getPrincipal();
        Favorite result = favoriteService.findById(id);
        if (result == null) {
            status = HttpStatus.NOT_FOUND;
        } else if (!Objects.equals(result.getUser_id(), user.getId())) {
            status = HttpStatus.FORBIDDEN;
        } else {
            try {
                favoriteService.delete(id);
            } catch (Exception e) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
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
