package net.rewerk.webstore.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.rewerk.webstore.configuration.pointer.ViewLevel;
import net.rewerk.webstore.model.entity.meta.DeletableEntityMeta;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "orders_products")
@JsonView(ViewLevel.RoleUser.class)
public class OrdersProducts extends DeletableEntityMeta {
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "product_id",
            referencedColumnName = "id",
            updatable = false,
            nullable = false
    )
    private Product product;
    @JsonIgnore
    @Column(
            nullable = false,
            updatable = false
    )
    private Integer order_id;
    private Integer amount;
}
