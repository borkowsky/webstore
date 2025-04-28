package net.rewerk.webstore.service;

import lombok.RequiredArgsConstructor;
import net.rewerk.webstore.model.dto.request.auth.LoginDto;
import net.rewerk.webstore.model.dto.request.auth.RegistrationDto;
import net.rewerk.webstore.model.dto.response.auth.AuthenticationResponseDto;
import net.rewerk.webstore.model.entity.User;
import net.rewerk.webstore.service.entity.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@RequiredArgsConstructor
@Service
public class AuthenticationService {
    private final UserService userService;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<AuthenticationResponseDto> register(@Validated RegistrationDto request) {
        HttpStatus status = HttpStatus.OK;
        String jwtToken = null;
        User user = null;
        try {
            user = User.builder()
                    .username(request.getUsername())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(User.Role.USER)
                    .balance(0.0)
                    .build();
            userService.create(user);
            jwtToken = jwtService.generateToken(user);
        } catch (Exception e) {
            status = HttpStatus.BAD_REQUEST;
        }
        AuthenticationResponseDto response = AuthenticationResponseDto.builder()
                .code(status.value())
                .message(status.getReasonPhrase())
                .token(jwtToken)
                .user(user)
                .build();
        return new ResponseEntity<>(response, status);
    }

    public ResponseEntity<AuthenticationResponseDto> authenticate(@Validated LoginDto request) {
        HttpStatus status = HttpStatus.OK;
        String jwtToken = null;
        User resultUser = null;
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()
            ));
            UserDetails user = userService.userDetailsService()
                    .loadUserByUsername(request.getUsername());
            resultUser = userService.getByUsername(user.getUsername());
            jwtToken = jwtService.generateToken(user);
        } catch (Exception e) {
            status = HttpStatus.UNAUTHORIZED;
        }
        AuthenticationResponseDto response = AuthenticationResponseDto.builder()
                .code(status.value())
                .message(status.getReasonPhrase())
                .token(jwtToken)
                .user(resultUser)
                .build();
        return new ResponseEntity<>(response, status);
    }
}
