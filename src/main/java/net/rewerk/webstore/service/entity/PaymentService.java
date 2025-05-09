package net.rewerk.webstore.service.entity;

import net.rewerk.webstore.model.entity.Payment;
import net.rewerk.webstore.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface PaymentService {
    Payment findById(Integer id);

    Page<Payment> findAll(Specification<Payment> specification, Pageable pageable);

    Payment create(Payment payment);

    Payment update(Payment payment, User user);

    Payment setAsPaid(Integer paymentId, User user);
}
