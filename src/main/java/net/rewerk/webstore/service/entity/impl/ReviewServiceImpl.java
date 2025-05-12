package net.rewerk.webstore.service.entity.impl;

import lombok.RequiredArgsConstructor;
import net.rewerk.webstore.exception.UnprocessableOperation;
import net.rewerk.webstore.model.entity.*;
import net.rewerk.webstore.repository.ReviewRepository;
import net.rewerk.webstore.service.entity.OrderService;
import net.rewerk.webstore.service.entity.ReviewService;
import net.rewerk.webstore.transport.dto.request.review.CreateDto;
import net.rewerk.webstore.transport.dto.request.review.ProbeRequestDto;
import net.rewerk.webstore.transport.dto.response.review.LastRatingsDto;
import net.rewerk.webstore.transport.dto.response.review.ProbeResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final OrderService orderService;

    @Override
    public Page<Review> findAll(Specification<Review> specification, Pageable pageable) {
        return reviewRepository.findAll(specification, pageable);
    }

    @Override
    public Review create(CreateDto createDto, User user) {
        if (!this.canReview(createDto.getOrder_id(), createDto.getProduct_id(), user)) {
            throw new UnprocessableOperation("Review already exists");
        }
        Order order = orderService.findByOrderIdAndUser(createDto.getOrder_id(), user);
        Product product = order.getProducts().stream()
                .map(OrdersProducts::getProduct)
                .filter(pProduct -> pProduct.getId().equals(createDto.getProduct_id()))
                .findFirst().orElseThrow(() -> new UnprocessableOperation("Product not found in order"));
        return reviewRepository.save(Review.builder()
                .images(createDto.getImages())
                .text(createDto.getText())
                .orderId(order.getId())
                .rating(createDto.getRating())
                .product(product)
                .user(user)
                .build());
    }

    @Override
    public ProbeResponseDto canReview(ProbeRequestDto probeDto, User user) {
        Order order = orderService.findByOrderIdAndUser(probeDto.getOrder_id(), user);
        return ProbeResponseDto.builder()
                .order_id(order.getId())
                .product_id(probeDto.getProduct_id())
                .allowed(this.canReview(order.getId(), probeDto.getProduct_id(), user))
                .build();
    }

    private boolean canReview(Integer orderId, Integer productId, User user) {
        return !reviewRepository.existsByOrderIdAndProduct_IdAndUser(orderId, productId, user);
    }

    @Override
    public LastRatingsDto findLastRatingsByProductId(Integer productId) {
        Map<Integer, Long> result = new HashMap<>();
        List<Integer> lastRatings = reviewRepository.findLastRatingsByProductId(productId);
        IntStream.range(1, 6).forEach(i -> result.put(i, (long) Collections.frequency(lastRatings, i)));
        return LastRatingsDto.builder()
                .total(lastRatings.size())
                .ratings(result)
                .build();
    }

    @Override
    public List<String> findAllImagesByProductId(Integer productId) {
        List<List<String>> images = reviewRepository.findAllImagesByProductId(productId);
        return images.stream().flatMap(Collection::stream).limit(20).toList();
    }
}
