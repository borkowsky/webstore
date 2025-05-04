package net.rewerk.webstore.model.entity;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.*;
import net.rewerk.webstore.configuration.pointer.ViewLevel;
import net.rewerk.webstore.model.entity.meta.EntityMeta;
import org.hibernate.annotations.Formula;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "categories")
@JsonView(ViewLevel.RoleAnonymous.class)
public class Category extends EntityMeta {
    @Column(nullable = false)
    private String name;
    private String description;
    private String icon;
    private Integer category_id;
    @OneToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.REMOVE,
            mappedBy = "category_id"
    )
    private List<Category> categories;
    @Formula("(select count(p.*) from products p where" +
            " (p.category_id in(select c.id from categories c where c.category_id = id)" +
            " or p.category_id = id) and p.deleted = false and p.enabled = true)")
    private Integer productsCount;
    private Boolean enabled;
}
