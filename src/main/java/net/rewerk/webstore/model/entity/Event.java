package net.rewerk.webstore.model.entity;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.*;
import net.rewerk.webstore.configuration.pointer.ViewLevel;
import net.rewerk.webstore.model.entity.meta.DeletableEntityMeta;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "events")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonView(ViewLevel.RoleAdministrator.class)
public class Event extends DeletableEntityMeta {
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            updatable = false
    )
    private User user;
    private String text;
}
