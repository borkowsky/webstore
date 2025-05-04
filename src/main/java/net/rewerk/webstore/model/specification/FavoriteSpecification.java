package net.rewerk.webstore.model.specification;

import lombok.NonNull;
import net.rewerk.webstore.model.dto.request.favorite.SearchDto;
import net.rewerk.webstore.model.entity.Favorite;
import net.rewerk.webstore.model.entity.Product;
import org.springframework.data.jpa.domain.Specification;

public abstract class FavoriteSpecification {
    public static Specification<Favorite> getSpecification(@NonNull Integer userId, @NonNull SearchDto searchDto) {
        return (root, cq, cb) -> cb.and(cb.equal(root.get("user_id"), userId));
    }

    public static Specification<Favorite> getSpecification(@NonNull Integer userId, @NonNull Product product) {
        return (root, cq, cb) -> cb.and(
                cb.equal(root.get("user_id"), userId),
                cb.equal(root.get("product").get("id"), product.getId())
        );
    }

    public static Specification<Favorite> getSpecification(@NonNull Integer userId, @NonNull Integer productId) {
        return (root, cq, cb) -> cb.and(
                cb.equal(root.get("user_id"), userId),
                cb.equal(root.get("product").get("id"), productId)
        );
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
