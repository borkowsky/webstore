package net.rewerk.webstore.service.entity;

import net.rewerk.webstore.model.entity.Review;
import net.rewerk.webstore.model.entity.User;
import net.rewerk.webstore.transport.dto.request.review.CreateDto;
import net.rewerk.webstore.transport.dto.request.review.ProbeRequestDto;
import net.rewerk.webstore.transport.dto.response.review.LastRatingsDto;
import net.rewerk.webstore.transport.dto.response.review.ProbeResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface ReviewService {
    Review create(CreateDto createDto, User user);

    ProbeResponseDto canReview(ProbeRequestDto probeDto, User user);

    Page<Review> findAll(Specification<Review> specification, Pageable pageable);

    List<String> findAllImagesByProductId(Integer productId);

    LastRatingsDto findLastRatingsByProductId(Integer productId);
}
