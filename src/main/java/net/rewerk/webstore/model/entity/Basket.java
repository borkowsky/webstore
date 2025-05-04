package net.rewerk.webstore.model.entity;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.rewerk.webstore.configuration.pointer.ViewLevel;
import net.rewerk.webstore.model.entity.meta.DeletableEntityMeta;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "basket")
@JsonView(ViewLevel.RoleUser.class)
public class Basket extends DeletableEntityMeta {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            updatable = false
    )
    private User user;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "product_id",
            nullable = false,
            updatable = false
    )
    private Product product;
    private Integer amount;
}
