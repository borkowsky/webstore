package net.rewerk.webstore.model.specification;

import lombok.NonNull;
import net.rewerk.webstore.model.entity.Basket;
import net.rewerk.webstore.model.entity.Product;
import net.rewerk.webstore.model.entity.User;
import org.springframework.data.jpa.domain.Specification;

public abstract class BasketSpecification {
    public static Specification<Basket> getSpecification(
            @NonNull User user,
            @NonNull Product product) {
        return (root, cq, cb) -> cb.and(
                cb.equal(root.get("user").get("id"), user.getId()),
                cb.equal(root.get("product").get("id"), product.getId())
        );
    }
}
