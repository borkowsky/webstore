package net.rewerk.webstore.util;

import net.rewerk.webstore.model.dto.response.common.PaginatedPayloadResponseDto;
import net.rewerk.webstore.model.dto.response.common.PayloadResponseDto;
import net.rewerk.webstore.model.dto.response.common.SinglePayloadResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public abstract class ResponseUtils {
    public static <T> ResponseEntity<PaginatedPayloadResponseDto<T>> createPaginatedResponse(
            Page<T> page
    ) {
        return ResponseEntity.ok(PaginatedPayloadResponseDto.<T>builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .payload(page.getContent())
                .total(page.getTotalElements())
                .page(page.getNumber() + 1)
                .pages(page.getTotalPages() + 1)
                .build());
    }

    public static <T> ResponseEntity<PayloadResponseDto<T>> createCollectionResponse(
            List<T> items
    ) {
        return ResponseEntity.ok(PayloadResponseDto.<T>builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .payload(items)
                .build());
    }

    public static <T> ResponseEntity<SinglePayloadResponseDto<T>> createSingleResponse(
            T item
    ) {
        return ResponseEntity.ok(SinglePayloadResponseDto.<T>builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .payload(item)
                .build());
    }
}
