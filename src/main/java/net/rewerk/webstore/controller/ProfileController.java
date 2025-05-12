package net.rewerk.webstore.controller;

import lombok.RequiredArgsConstructor;
import net.rewerk.webstore.model.entity.Address;
import net.rewerk.webstore.service.entity.AddressService;
import net.rewerk.webstore.transport.dto.response.common.PayloadResponseDto;
import net.rewerk.webstore.transport.dto.response.common.SinglePayloadResponseDto;
import net.rewerk.webstore.model.entity.User;
import net.rewerk.webstore.util.ResponseUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/me")
@RestController
public class ProfileController {
    private final AddressService addressService;

    @GetMapping
    public ResponseEntity<SinglePayloadResponseDto<User>> getProfile(
            @AuthenticationPrincipal UserDetails user
    ) {
        return ResponseUtils.createSingleResponse((User) user);
    }

    @GetMapping("/addresses")
    public ResponseEntity<PayloadResponseDto<Address>> getProfileAddresses(
            @AuthenticationPrincipal UserDetails user
    ) {
        List<Address> addresses = addressService.findByUser((User) user);
        return ResponseUtils.createCollectionResponse(addresses);
    }
}
