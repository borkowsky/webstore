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
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "addresses")
@JsonView(ViewLevel.RoleUser.class)
public class Address extends DeletableEntityMeta {
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String region;
    private Integer postalCode;
    @Column(nullable = false)
    private String country;
    @JsonIgnore
    @Column(nullable = false, updatable = false)
    private Integer userId;
}
