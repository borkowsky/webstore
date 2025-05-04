package net.rewerk.webstore.model.entity;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.*;
import net.rewerk.webstore.configuration.pointer.ViewLevel;
import net.rewerk.webstore.model.entity.meta.EntityMeta;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
@JsonView(ViewLevel.RoleAnonymous.class)
public class Product extends EntityMeta {
    private String name;
    private String description;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "category_id",
            nullable = false
    )
    private Category category;
    private Double price;
    private Double discountPrice;
    private Double rating;
    private Integer balance;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "brand_id")
    private Brand brand;
    private List<String> images;
    private List<String> tags;
    @JsonView(ViewLevel.RoleAdministrator.class)
    private Boolean enabled;
}
