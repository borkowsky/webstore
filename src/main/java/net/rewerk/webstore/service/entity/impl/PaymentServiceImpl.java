package net.rewerk.webstore.service.entity.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import net.rewerk.webstore.exception.UnprocessableOperation;
import net.rewerk.webstore.model.entity.Order;
import net.rewerk.webstore.model.entity.Payment;
import net.rewerk.webstore.model.entity.User;
import net.rewerk.webstore.repository.PaymentRepository;
import net.rewerk.webstore.service.entity.OrderService;
import net.rewerk.webstore.service.entity.PaymentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final OrderService orderService;

    @Override
    public Payment findById(Integer id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found"));
    }

    @Override
    public Page<Payment> findAll(Specification<Payment> specification, Pageable pageable) {
        return paymentRepository.findAll(specification, pageable);
    }

    @Override
    public Payment create(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public Payment update(Payment payment, User user) {
        return paymentRepository.save(payment);
    }

    @Override
    public Payment setAsPaid(Integer paymentId, User user) {
        Payment payment = paymentRepository.findByIdAndUserId(paymentId, user.getId())
                .orElseThrow(() -> new EntityNotFoundException("Payment not found"));
        Order order = orderService.findByPaymentIdAndUser(paymentId, user);
        if (List.of(Order.Status.PAID, Order.Status.RECEIVED).contains(order.getStatus())) {
            throw new UnprocessableOperation("Order status conflict");
        }
        payment.setStatus(Payment.Status.APPROVED);
        order.setStatus(Order.Status.PAID);
        orderService.update(order);
        return paymentRepository.save(payment);
    }
}
