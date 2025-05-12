package net.rewerk.webstore.repository;

import net.rewerk.webstore.model.entity.Order;
import net.rewerk.webstore.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>, JpaSpecificationExecutor<Order> {
    Optional<Order> findByIdAndUserId(Integer id, Integer userId);

    Optional<Order> findByPaymentIdAndUser(Integer paymentId, User user);
}
