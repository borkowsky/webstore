package net.rewerk.webstore.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.rewerk.webstore.model.dto.request.auth.LoginDto;
import net.rewerk.webstore.model.dto.response.auth.AuthenticationResponseDto;
import net.rewerk.webstore.model.dto.request.auth.RegistrationDto;
import net.rewerk.webstore.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDto> login(@Valid @RequestBody LoginDto loginDto) {
        return authenticationService.authenticate(loginDto);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDto> register(
            @Valid @RequestBody RegistrationDto registerDto
            ) {
        return authenticationService.register(registerDto);
    }
}
