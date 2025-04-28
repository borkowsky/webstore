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
@Table(name = "categories")
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
    private Boolean enabled;
}
