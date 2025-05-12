package net.rewerk.webstore.service.entity;

import net.rewerk.webstore.model.entity.Order;
import net.rewerk.webstore.model.entity.User;
import net.rewerk.webstore.transport.dto.request.order.CreateDto;
import net.rewerk.webstore.transport.dto.request.order.PatchDto;
import net.rewerk.webstore.transport.dto.request.order.SearchDto;
import net.rewerk.webstore.transport.dto.response.order.CountersDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface OrderService {
    Order findById(Integer id);

    Order findByOrderIdAndUser(Integer orderId, User user);

    Order findByPaymentIdAndUser(Integer paymentId, User user);

    CountersDto getCounters(SearchDto dto, User user);

    Order create(CreateDto createDto, User user);

    Order update(Order order);

    Order update(Order order, PatchDto patchDto);

    void delete(Integer id, User user);

    Page<Order> findAll(Specification<Order> specification, Pageable pageable);
}
