package net.rewerk.webstore.model.specification;

import jakarta.persistence.criteria.Predicate;
import lombok.NonNull;
import net.rewerk.webstore.transport.dto.request.category.SearchDto;
import net.rewerk.webstore.model.entity.Category;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public abstract class CategorySpecification {
    public static Specification<Category> getSpecification(@NonNull SearchDto searchDto) {
        return (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (searchDto.getCategory_id() == null) {
                predicates.add(cb.isNull(root.get("categoryId")));
            } else if (searchDto.getCategory_id() > 0) {
                predicates.add(cb.equal(root.get("categoryId"), searchDto.getCategory_id()));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
