package net.rewerk.webstore.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.rewerk.webstore.model.dto.request.address.PatchDto;
import net.rewerk.webstore.model.entity.Address;
import net.rewerk.webstore.model.entity.User;
import net.rewerk.webstore.service.entity.AddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/addresses/{id:\\d+}")
public class AddressController {
    private final AddressService addressService;

    @ModelAttribute("address")
    public Address getAddress(@PathVariable Integer id,
                              @AuthenticationPrincipal UserDetails userDetails) {
        return addressService.findById(id, (User) userDetails);
    }

    @PatchMapping
    public ResponseEntity<Void> updateAddress(@Valid @RequestBody PatchDto patchDto,
                                              @AuthenticationPrincipal UserDetails userDetails,
                                              @ModelAttribute("address") Address address
    ) {
        addressService.update(address, patchDto, (User) userDetails);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAddress(@AuthenticationPrincipal UserDetails userDetails,
                                              @ModelAttribute("address") Address address) {
        addressService.delete(address, (User) userDetails);
        return ResponseEntity.noContent().build();
    }
}
