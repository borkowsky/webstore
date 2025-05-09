package net.rewerk.webstore.controller;

import lombok.RequiredArgsConstructor;
import net.rewerk.webstore.model.dto.response.common.SinglePayloadResponseDto;
import net.rewerk.webstore.model.entity.Favorite;
import net.rewerk.webstore.model.entity.User;
import net.rewerk.webstore.service.entity.FavoriteService;
import net.rewerk.webstore.util.ResponseUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/favorites/{id:\\d+}")
@RestController
@RequiredArgsConstructor
public class FavoriteController {
    private final FavoriteService favoriteService;

    @ModelAttribute("favorite")
    public Favorite getFavorite(@PathVariable Integer id) {
        return favoriteService.findById(id);
    }

    @GetMapping
    public ResponseEntity<SinglePayloadResponseDto<Favorite>> getFavorite(
            @ModelAttribute("favorite") Favorite favorite
    ) {
        return ResponseUtils.createSingleResponse(favorite);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteFavorite(
            @ModelAttribute Favorite favorite,
            Authentication authentication
    ) {
        favoriteService.delete((User) authentication.getPrincipal(), favorite);
        return ResponseEntity.noContent().build();
    }
}
