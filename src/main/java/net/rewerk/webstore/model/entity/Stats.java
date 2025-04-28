package net.rewerk.webstore.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.rewerk.webstore.model.entity.meta.DeletableEntityMeta;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "stats")
public class Stats extends DeletableEntityMeta {
    private Integer users;
    private Integer orders;
    private Integer reviews;
    private Double paid;
}
