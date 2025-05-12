package net.rewerk.webstore.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.*;
import net.rewerk.webstore.configuration.pointer.ViewLevel;
import net.rewerk.webstore.model.entity.meta.DeletableEntityMeta;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@Entity
@Table(name = "favorites")
@JsonView(ViewLevel.RoleUser.class)
@NoArgsConstructor
@AllArgsConstructor
public class Favorite extends DeletableEntityMeta {
    @JsonIgnore
    private Integer userId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "product_id",
            nullable = false,
            updatable = false
    )
    private Product product;
}
