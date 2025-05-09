package net.rewerk.webstore.controller;

import net.rewerk.webstore.model.dto.response.common.SinglePayloadResponseDto;
import net.rewerk.webstore.model.entity.User;
import net.rewerk.webstore.util.ResponseUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/me")
@RestController
public class ProfileController {
    @GetMapping
    public ResponseEntity<SinglePayloadResponseDto<User>> getProfile(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return ResponseUtils.createSingleResponse(user);
    }
}
