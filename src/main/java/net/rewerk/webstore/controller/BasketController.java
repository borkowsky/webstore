package net.rewerk.webstore.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.rewerk.webstore.model.dto.request.basket.PatchDto;
import net.rewerk.webstore.model.entity.Basket;
import net.rewerk.webstore.model.entity.User;
import net.rewerk.webstore.service.entity.BasketService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/basket/{id:\\d+}")
@RestController
@RequiredArgsConstructor
public class BasketController {
    private final BasketService basketService;

    @ModelAttribute("basket")
    public Basket getBasket(@PathVariable Integer id) {
        return basketService.findById(id);
    }

    @PatchMapping
    public ResponseEntity<Void> updateBasket(
            @ModelAttribute("basket") Basket currentBasket,
            @Valid @RequestBody PatchDto patchDto,
            @AuthenticationPrincipal UserDetails user
            ) {
        basketService.update(currentBasket, patchDto, (User) user);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteBasket(
            @ModelAttribute("basket") Basket basket,
            Authentication authentication
    ) {
        basketService.delete(basket, (User) authentication.getPrincipal());
        return ResponseEntity.noContent().build();
    }
}
