package net.rewerk.webstore.model.entity;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.rewerk.webstore.configuration.pointer.ViewLevel;
import net.rewerk.webstore.model.entity.meta.EntityMeta;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "reviews")
@JsonView(ViewLevel.RoleAnonymous.class)
public class Review extends EntityMeta {
    private Integer rating;
    private String text;
    @OneToOne(fetch = FetchType.EAGER)
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
    private String image;
}
