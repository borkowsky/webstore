package net.rewerk.webstore.model.entity;

import jakarta.persistence.*;
import lombok.*;
import net.rewerk.webstore.model.entity.meta.EntityMeta;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
public class Product extends EntityMeta {
    private String name;
    private String description;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(
            name = "category_id",
            nullable = false
    )
    private Category category;
    private Double price;
    private Double discountPrice;
    private Integer balance;
    private List<String> images;
    private Boolean enabled;
}
