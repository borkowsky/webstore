package net.rewerk.webstore.model.specification;

import lombok.NonNull;
import net.rewerk.webstore.model.entity.Favorite;
import org.springframework.data.jpa.domain.Specification;

public abstract class FavoriteSpecification {
    public static Specification<Favorite> getSpecification(@NonNull Integer userId) {
        return (root, cq, cb) -> cb.equal(root.get("user_id"), userId);
    }

    public static Specification<Favorite> getCategoriesSpecification(@NonNull Integer userId) {
        return (root, cq, cb) -> {
            if (cq != null) {
                cq.select(root.get("product").get("category").get("id"));
            }
            return cb.equal(root.get("user_id"), userId);
        };
    }
}
