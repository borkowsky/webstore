package net.rewerk.webstore.model.specification;

import jakarta.persistence.criteria.Predicate;
import jakarta.validation.constraints.NotNull;
import net.rewerk.webstore.model.entity.Review;
import net.rewerk.webstore.transport.dto.request.review.SearchDto;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public abstract class ReviewSpecification {
    public static Specification<Review> getSpecification(@NotNull SearchDto searchDto) {
        return (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (searchDto.getUser_id() != null) {
                predicates.add(cb.equal(root.get("user").get("id"), searchDto.getUser_id()));
            }
            if (searchDto.getProduct_id() != null) {
                predicates.add(cb.equal(root.get("product").get("id"), searchDto.getProduct_id()));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
