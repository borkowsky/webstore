package net.rewerk.webstore.controller;

import net.rewerk.webstore.model.dto.response.common.SinglePayloadResponseDto;
import net.rewerk.webstore.model.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/me")
@RestController
public class ProfileController {
    @GetMapping({"", "/"})
    public ResponseEntity<SinglePayloadResponseDto<User>> index(Authentication authentication) {
        HttpStatus status = HttpStatus.OK;
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(SinglePayloadResponseDto.<User>builder()
                        .code(status.value())
                        .message(status.getReasonPhrase())
                        .payload(user)
                .build());
    }
}
