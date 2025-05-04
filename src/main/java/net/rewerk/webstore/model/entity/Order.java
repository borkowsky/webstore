package net.rewerk.webstore.model.entity;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.rewerk.webstore.configuration.pointer.ViewLevel;
import net.rewerk.webstore.model.entity.meta.EntityMeta;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "orders")
@JsonView(ViewLevel.RoleUser.class)
public class Order extends EntityMeta {
    public enum Status {
        CREATED,
        PAID,
        ACCEPTED,
        REJECTED,
        DELIVERY,
        DELIVERED,
        RECEIVED
    }

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            updatable = false
    )
    private User user;
    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "order_id",
            cascade = CascadeType.REMOVE
    )
    private List<OrdersProducts> products;
    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private Status status;
}
