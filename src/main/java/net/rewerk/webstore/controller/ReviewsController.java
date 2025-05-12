package net.rewerk.webstore.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.rewerk.webstore.model.entity.Review;
import net.rewerk.webstore.model.entity.User;
import net.rewerk.webstore.model.specification.ReviewSpecification;
import net.rewerk.webstore.service.entity.ReviewService;
import net.rewerk.webstore.transport.dto.request.review.CreateDto;
import net.rewerk.webstore.transport.dto.request.review.ProbeRequestDto;
import net.rewerk.webstore.transport.dto.request.review.SearchDto;
import net.rewerk.webstore.transport.dto.response.common.PaginatedPayloadResponseDto;
import net.rewerk.webstore.transport.dto.response.common.PayloadResponseDto;
import net.rewerk.webstore.transport.dto.response.common.SinglePayloadResponseDto;
import net.rewerk.webstore.transport.dto.response.review.LastRatingsDto;
import net.rewerk.webstore.transport.dto.response.review.ProbeResponseDto;
import net.rewerk.webstore.util.RequestUtils;
import net.rewerk.webstore.util.ResponseUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewsController {
    private final ReviewService reviewService;

    @GetMapping
    public ResponseEntity<PaginatedPayloadResponseDto<Review>> findReviews(@Valid SearchDto searchDto) {
        Page<Review> reviews = reviewService.findAll(
                ReviewSpecification.getSpecification(searchDto),
                RequestUtils.getSortAndPageRequest(searchDto)
        );
        return ResponseUtils.createPaginatedResponse(reviews);
    }

    @GetMapping("/images")
    public ResponseEntity<PayloadResponseDto<String>> findReviewsImages(@Valid SearchDto searchDto) {
        List<String> result = reviewService.findAllImagesByProductId(searchDto.getProduct_id());
        return ResponseUtils.createCollectionResponse(result);
    }

    @GetMapping("/last-ratings")
    public ResponseEntity<SinglePayloadResponseDto<LastRatingsDto>> findReviewsLastRatings(
            @Valid SearchDto searchDto
    ) {
        return ResponseUtils.createSingleResponse(reviewService.findLastRatingsByProductId(searchDto.getProduct_id()));
    }

    @PostMapping
    public ResponseEntity<SinglePayloadResponseDto<Review>> createReview(
            @Valid @RequestBody CreateDto createDto,
            @AuthenticationPrincipal UserDetails userDetails,
            UriComponentsBuilder uriBuilder
    ) {
        Review review = reviewService.create(createDto, (User) userDetails);
        return ResponseEntity.created(uriBuilder.replacePath("/api/v1/reviews/${reviewId}")
                .build(Map.of("reviewId", review.getId()))
        ).body(SinglePayloadResponseDto.<Review>builder()
                .code(HttpStatus.CREATED.value())
                .message(HttpStatus.CREATED.getReasonPhrase())
                .payload(review)
                .build());
    }

    @PostMapping("/probe")
    public ResponseEntity<SinglePayloadResponseDto<ProbeResponseDto>> probeReview(
            @Valid @RequestBody ProbeRequestDto probeDto,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return ResponseUtils.createSingleResponse(reviewService.canReview(probeDto, (User) userDetails));
    }
}
