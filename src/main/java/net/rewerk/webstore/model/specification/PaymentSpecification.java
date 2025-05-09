package net.rewerk.webstore.model.specification;

import jakarta.persistence.criteria.Predicate;
import lombok.NonNull;
import net.rewerk.webstore.model.dto.request.payment.SearchDto;
import net.rewerk.webstore.model.entity.Payment;
import net.rewerk.webstore.model.entity.User;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public abstract class PaymentSpecification {
    public static Specification<Payment> getSpecification(@NonNull SearchDto searchDto,
                                                          @NonNull User user) {
        return (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("userId"), user.getId()));
            if (searchDto.getStatus() != null) {
                if ("pending".equals(searchDto.getStatus())) {
                    predicates.add(cb.equal(root.get("status"), Payment.Status.CREATED.toString()));
                } else {
                    predicates.add(cb.notEqual(root.get("status"), Payment.Status.CREATED.toString()));
                }
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
