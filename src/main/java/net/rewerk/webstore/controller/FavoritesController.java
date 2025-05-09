package net.rewerk.webstore.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.rewerk.webstore.model.dto.request.favorite.CreateDto;
import net.rewerk.webstore.model.dto.request.favorite.SearchDto;
import net.rewerk.webstore.model.dto.response.common.PaginatedPayloadResponseDto;
import net.rewerk.webstore.model.dto.response.common.PayloadResponseDto;
import net.rewerk.webstore.model.dto.response.common.SinglePayloadResponseDto;
import net.rewerk.webstore.model.entity.Category;
import net.rewerk.webstore.model.entity.Favorite;
import net.rewerk.webstore.model.entity.User;
import net.rewerk.webstore.model.specification.FavoriteSpecification;
import net.rewerk.webstore.service.entity.CategoryService;
import net.rewerk.webstore.service.entity.FavoriteService;
import net.rewerk.webstore.util.RequestUtils;
import net.rewerk.webstore.util.ResponseUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/favorites")
public class FavoritesController {
    private final FavoriteService favoriteService;
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<PaginatedPayloadResponseDto<Favorite>> findAllFavorites(
            @Valid SearchDto request,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();
        Page<Favorite> result = favoriteService.findAll(
                FavoriteSpecification.getSpecification(user.getId()),
                RequestUtils.getSortAndPageRequest(request)
        );
        return ResponseUtils.createPaginatedResponse(result);
    }

    @GetMapping("/categories")
    public ResponseEntity<PayloadResponseDto<Category>> findFavoriteCategories(
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();
        List<Integer> categoriesIds = favoriteService.findCategoriesIds(
                FavoriteSpecification.getCategoriesSpecification(user.getId())
        );
        List<Category> categories = categoryService.findAllDistinctById(categoriesIds);
        return ResponseUtils.createCollectionResponse(categories);
    }

    @PostMapping
    public ResponseEntity<SinglePayloadResponseDto<Favorite>> createFavorite(
            @Valid @RequestBody CreateDto request,
            UriComponentsBuilder uriBuilder,
            Authentication authentication
    ) {
        Favorite result = favoriteService.create(request, (User) authentication.getPrincipal());
        return ResponseEntity.created(uriBuilder
                .replacePath("/api/v1/favorites/{favoriteId}")
                .build(Map.of("favoriteId", result.getId()))
        ).body(SinglePayloadResponseDto.<Favorite>builder()
                .code(HttpStatus.CREATED.value())
                .message(HttpStatus.CREATED.getReasonPhrase())
                .payload(result)
                .build());
    }
}
