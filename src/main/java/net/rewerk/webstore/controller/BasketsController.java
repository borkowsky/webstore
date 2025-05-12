package net.rewerk.webstore.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.rewerk.webstore.transport.dto.request.basket.CreateDto;
import net.rewerk.webstore.transport.dto.request.basket.MultipleDeleteDto;
import net.rewerk.webstore.transport.dto.request.basket.SearchDto;
import net.rewerk.webstore.transport.dto.response.common.PaginatedPayloadResponseDto;
import net.rewerk.webstore.transport.dto.response.common.PayloadResponseDto;
import net.rewerk.webstore.transport.dto.response.common.SinglePayloadResponseDto;
import net.rewerk.webstore.model.entity.Basket;
import net.rewerk.webstore.model.entity.Category;
import net.rewerk.webstore.model.entity.User;
import net.rewerk.webstore.model.specification.BasketSpecification;
import net.rewerk.webstore.service.entity.BasketService;
import net.rewerk.webstore.service.entity.CategoryService;
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
@RequestMapping("/api/v1/basket")
public class BasketsController {
    private final BasketService basketService;
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<PaginatedPayloadResponseDto<Basket>> findAllBaskets(
            @Valid SearchDto request,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();
        Page<Basket> result = basketService.findAll(
                BasketSpecification.getUserSpecification(user),
                RequestUtils.getPageRequest(request)
        );
        return ResponseUtils.createPaginatedResponse(result);
    }

    @GetMapping("/categories")
    public ResponseEntity<PayloadResponseDto<Category>> findBasketCategories(
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();
        List<Integer> categoryIds = basketService.findCategoriesIds(
                BasketSpecification.getCategoriesSpecification(user.getId())
        );
        List<Category> categories = categoryService.findAllDistinctById(categoryIds);
        return ResponseUtils.createCollectionResponse(categories);
    }

    @PostMapping
    public ResponseEntity<SinglePayloadResponseDto<Basket>> createBasket(
            @Valid @RequestBody CreateDto request,
            UriComponentsBuilder uriBuilder,
            Authentication authentication
    ) {
        Basket result = basketService.create(request, (User) authentication.getPrincipal());
        return ResponseEntity.created(uriBuilder.replacePath("/api/v1/basket/{basketId}").build(
                        Map.of("basketId", result.getId())
                ))
                .body(SinglePayloadResponseDto.<Basket>builder()
                        .code(HttpStatus.CREATED.value())
                        .message(HttpStatus.CREATED.getReasonPhrase())
                        .payload(result)
                        .build());
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteBaskets(
            @Valid @RequestBody MultipleDeleteDto request,
            Authentication authentication
    ) {
        basketService.deleteAllById(request, (User) authentication.getPrincipal());
        return ResponseEntity.noContent().build();
    }
}
