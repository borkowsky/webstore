package net.rewerk.webstore.model.specification;

import jakarta.persistence.criteria.Predicate;
import net.rewerk.webstore.model.dto.request.product.SearchDto;
import net.rewerk.webstore.model.entity.Product;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public abstract class ProductSpecification {
    public static Specification<Product> getSpecification(SearchDto searchDto) {
        return (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (searchDto != null) {
                if (searchDto.getCategory_id() == null) {
                    predicates.add(cb.isNull(root.get("category")));
                } else {
                    predicates.add(cb.equal(root.get("category").get("id"), searchDto.getCategory_id()));
                }
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
