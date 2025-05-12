package net.rewerk.webstore.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.rewerk.webstore.model.entity.User;
import net.rewerk.webstore.service.entity.UserService;
import net.rewerk.webstore.transport.dto.request.user.SearchDto;
import net.rewerk.webstore.transport.dto.response.common.PayloadResponseDto;
import net.rewerk.webstore.util.ResponseUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UsersController {
    private final UserService userService;

    @GetMapping("/search")
    public ResponseEntity<PayloadResponseDto<User>> searchUsers(
            @Valid SearchDto searchDto
            ) {
        List<User> result = userService.searchByUsername(searchDto);
        return ResponseUtils.createCollectionResponse(result);
    }
}
