package net.rewerk.webstore.service.entity.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import net.rewerk.webstore.exception.UnprocessableOperation;
import net.rewerk.webstore.model.entity.*;
import net.rewerk.webstore.model.specification.OrderSpecification;
import net.rewerk.webstore.repository.OrderRepository;
import net.rewerk.webstore.service.entity.AddressService;
import net.rewerk.webstore.service.entity.BasketService;
import net.rewerk.webstore.service.entity.OrderService;
import net.rewerk.webstore.transport.dto.request.order.CreateDto;
import net.rewerk.webstore.transport.dto.request.order.PatchDto;
import net.rewerk.webstore.transport.dto.request.order.SearchDto;
import net.rewerk.webstore.transport.dto.response.order.CountersDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final BasketService basketService;
    private final AddressService addressService;

    @Override
    public Order findById(Integer id) {
        return orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order not found"));
    }

    @Override
    public Order findByOrderIdAndUser(Integer orderId, User user) {
        return orderRepository.findByIdAndUserId(orderId, user.getId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
    }

    @Override
    public Order findByPaymentIdAndUser(Integer paymentId, User user) {
        return orderRepository.findByPaymentIdAndUser(paymentId, user)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
    }

    @Override
    public CountersDto getCounters(SearchDto dto, User user) {
        SearchDto activeDto = dto.clone();
        SearchDto completedDto = dto.clone();
        activeDto.setType("active");
        completedDto.setType("completed");
        Long activeTotal = orderRepository.count(OrderSpecification.getSpecification(user, activeDto));
        Long completedTotal = orderRepository.count(OrderSpecification.getSpecification(user, completedDto));
        return CountersDto.builder()
                .active(activeTotal)
                .completed(completedTotal)
                .build();
    }

    @Override
    public Order create(CreateDto createDto, User user) {
        List<Address> addresses = addressService.findByUser(user);
        if (addresses.isEmpty()) {
            throw new UnprocessableOperation("User not have any address");
        }
        Address address = addresses.stream()
                .filter(a -> Objects.equals(a.getId(), createDto.getAddress_id()))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Address not found"));
        List<Basket> baskets = basketService.findAddByIdsInAndUserId(
                Arrays.asList(createDto.getBasket_ids()), user.getId()
        );
        if (baskets.isEmpty()) {
            throw new EntityNotFoundException("Basket not found");
        }
        Double totalSum = baskets.stream()
                .filter(basket -> Objects.nonNull(basket.getProduct()))
                .mapToDouble(
                        basket -> basket.getAmount() * (
                                basket.getProduct().getDiscountPrice() != null
                                        && basket.getProduct().getDiscountPrice() < basket.getProduct().getPrice() ?
                                        basket.getProduct().getDiscountPrice() :
                                        basket.getProduct().getPrice()
                        )
                )
                .sum();
        Order order = Order.builder()
                .user(user)
                .status(Order.Status.CREATED)
                .payment(Payment.builder()
                        .status(Payment.Status.CREATED)
                        .sum(totalSum)
                        .userId(user.getId())
                        .build())
                .products(baskets.stream()
                        .map(basket -> OrdersProducts.builder()
                                .product(basket.getProduct())
                                .amount(basket.getAmount())
                                .build())
                        .toList()
                )
                .address(address)
                .build();
        basketService.deleteAllById(baskets.stream().map(Basket::getId).toList());
        return orderRepository.save(order);
    }

    @Override
    public Order update(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order update(Order order, PatchDto patchDto) {
        if (patchDto.getOrder_status() != null) {
            order.setStatus(patchDto.getOrder_status());
        }
        if (patchDto.getPayment_status() != null) {
            order.getPayment().setStatus(patchDto.getPayment_status());
        }
        return orderRepository.save(order);
    }

    @Override
    public void delete(Integer id, User user) {
        orderRepository.findById(id)
                .ifPresentOrElse(order -> {
                            if (Objects.equals(order.getUser().getId(), user.getId())) {
                                orderRepository.delete(order);
                            }
                        },
                        () -> {
                            throw new EntityNotFoundException("Order not found");
                        });
    }

    @Override
    public Page<Order> findAll(Specification<Order> specification, Pageable pageable) {
        return orderRepository.findAll(specification, pageable);
    }
}
