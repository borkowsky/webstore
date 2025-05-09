package net.rewerk.webstore.model.specification;

import jakarta.persistence.criteria.Predicate;
import lombok.NonNull;
import net.rewerk.webstore.model.dto.request.order.SearchDto;
import net.rewerk.webstore.model.entity.Order;
import net.rewerk.webstore.model.entity.User;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public abstract class OrderSpecification {
    public static Specification<Order> getSpecification(@NonNull User user,
                                                        @NonNull SearchDto searchDto
    ) {
        return (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (user.getRole().equals(User.Role.USER)) {
                predicates.add(cb.equal(root.get("user"), user));
            } else if (searchDto.getUser_id() != null) {
                predicates.add(cb.equal(root.get("user").get("id"), searchDto.getUser_id()));
            }
            if (searchDto.getType() != null) {
                if ("active".equals(searchDto.getType())) {
                    predicates.add(
                            cb.not(root.get("status").in(List.of(
                                    Order.Status.RECEIVED
                            )))
                    );
                } else {
                    predicates.add(
                            cb.in(root.get("status").in(List.of(
                                    Order.Status.RECEIVED
                            )))
                    );
                }
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
