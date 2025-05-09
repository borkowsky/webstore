package net.rewerk.webstore.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.*;
import net.rewerk.webstore.configuration.pointer.ViewLevel;
import net.rewerk.webstore.model.entity.meta.DeletableEntityMeta;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "basket")
@JsonView(ViewLevel.RoleUser.class)
public class Basket extends DeletableEntityMeta {
    @JsonIgnore
    @Column(nullable = false, updatable = false)
    private Integer userId;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "product_id",
            nullable = false,
            updatable = false
    )
    private Product product;
    @Column(nullable = false)
    private Integer amount;

    @JsonProperty("can_inc_amount")
    private Boolean canIncAmount() {
        return this.product.getBalance() > 0;
    }

    @JsonProperty("can_dec_amount")
    private Boolean canDecAmount() {
        return this.amount > 1;
    }
}
