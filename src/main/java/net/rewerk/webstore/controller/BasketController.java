package net.rewerk.webstore.controller;

import net.rewerk.webstore.model.dto.response.common.PaginatedPayloadResponseDto;
import net.rewerk.webstore.model.entity.Basket;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/basket")
@RestController
public class BasketController {
    @GetMapping({"", "/"})
    public ResponseEntity<PaginatedPayloadResponseDto<Basket>> index() {
        return ResponseEntity.ok(
                PaginatedPayloadResponseDto.<Basket>builder()
                        .code(HttpStatus.OK.value())
                        .message(HttpStatus.OK.getReasonPhrase())
                        .build()
        );
    }
}
