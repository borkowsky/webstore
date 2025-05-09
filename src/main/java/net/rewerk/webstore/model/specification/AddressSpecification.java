package net.rewerk.webstore.model.specification;

import lombok.NonNull;
import net.rewerk.webstore.model.entity.Address;
import net.rewerk.webstore.model.entity.User;
import org.springframework.data.jpa.domain.Specification;

public abstract class AddressSpecification {
    public static Specification<Address> getSpecification(@NonNull User user) {
        return (root, cq, cb) -> cb.equal(root.get("userId"), user.getId());
    }
}
