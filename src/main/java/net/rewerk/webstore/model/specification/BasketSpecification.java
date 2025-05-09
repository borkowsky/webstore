package net.rewerk.webstore.model.specification;

import lombok.NonNull;
import net.rewerk.webstore.model.entity.Basket;
import net.rewerk.webstore.model.entity.User;
import org.springframework.data.jpa.domain.Specification;

public abstract class BasketSpecification {
    public static Specification<Basket> getUserSpecification(
            @NonNull User user) {
        return (root, cq, cb) ->
                cb.equal(root.get("userId"), user.getId());
    }

    public static Specification<Basket> getCategoriesSpecification(@NonNull Integer userId) {
        return (root, cq, cb) -> {
            if (cq != null) {
                cq.select(root.get("product").get("category").get("id"));
            }
            return cb.equal(root.get("userId"), userId);
        };
    }
}
