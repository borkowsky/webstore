package net.rewerk.webstore.model.specification;

import jakarta.persistence.criteria.Predicate;
import lombok.NonNull;
import net.rewerk.webstore.model.dto.request.product.SearchDto;
import net.rewerk.webstore.model.entity.Product;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public abstract class ProductSpecification {
    public static Specification<Product> getSpecification(@NonNull SearchDto searchDto) {
        return (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (searchDto.getCategory_id() == null) {
                predicates.add(cb.isNull(root.get("category")));
            } else if (searchDto.getCategory_id() > 0) {
                predicates.add(cb.equal(root.get("category").get("id"), searchDto.getCategory_id()));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Product> getPopularSpecification() {
        return (root, cq, cb) -> cb.greaterThan(root.get("rating"), 0);
    }
}
