package net.rewerk.webstore.model.specification;

import jakarta.persistence.criteria.Predicate;
import lombok.NonNull;
import net.rewerk.webstore.transport.dto.request.brand.SearchDto;
import net.rewerk.webstore.model.entity.Brand;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public abstract class BrandSpecification {
    public static Specification<Brand> getSpecification(@NonNull SearchDto searchDto) {
        return (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (searchDto.getName_query() != null) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get("name")),
                                cb.lower(
                                        cb.literal("%" + searchDto.getName_query() + "%")
                                )
                        )
                );
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
