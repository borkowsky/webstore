package net.rewerk.webstore.controller;

import lombok.RequiredArgsConstructor;
import net.rewerk.webstore.model.dto.response.common.PaginatedPayloadResponseDto;
import net.rewerk.webstore.model.entity.Event;
import net.rewerk.webstore.service.entity.EventService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/events")
@RestController
public class EventController {
    private final EventService eventService;

    @GetMapping({"", "/"})
    public ResponseEntity<PaginatedPayloadResponseDto<Event>> index(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "limit", defaultValue = "20") Integer limit,
            @RequestParam(value = "sort", defaultValue = "id") String sort,
            @RequestParam(value = "order", defaultValue = "1") Integer order
    ) {
        Page<Event> events = eventService.findAll(
                PageRequest.of(
                        page,
                        limit,
                        sort == null ? Sort.unsorted() :
                                Sort.by(order == 1 ? Sort.Direction.ASC : Sort.Direction.DESC, sort)
                )
        );
        return ResponseEntity.ok(PaginatedPayloadResponseDto.<Event>builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .payload(events.getContent())
                .page(events.getNumber() + 1)
                .pages(events.getTotalPages())
                .size(events.getSize())
                .build());
    }
}
