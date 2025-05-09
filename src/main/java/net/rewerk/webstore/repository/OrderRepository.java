package net.rewerk.webstore.repository;

import net.rewerk.webstore.model.entity.Order;
import net.rewerk.webstore.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Collection;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer>, JpaSpecificationExecutor<Order> {
    Order findByIdAndUserId(Integer id, Integer userId);

    Long countOrderByStatusInAndUserId(Collection<Order.Status> statuses, Integer userId);

    Optional<Order> findByPaymentIdAndUser(Integer paymentId, User user);
}
