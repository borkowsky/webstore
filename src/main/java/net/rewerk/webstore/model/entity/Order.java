package net.rewerk.webstore.model.entity;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.*;
import net.rewerk.webstore.configuration.pointer.ViewLevel;
import net.rewerk.webstore.model.entity.meta.EntityMeta;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            updatable = false
    )
    private User user;
    @OneToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.PERSIST
    )
    @JoinColumn(
            name = "order_id"
    )
    private List<OrdersProducts> products;
    @OneToOne(
            fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.PERSIST
            }
    )
    @JoinColumn(
            name = "payment_id",
            nullable = false,
            updatable = false
    )
    private Payment payment;
    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(
            name = "address_id",
            updatable = false,
            nullable = false
    )
    private Address address;
    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private Status status;
}
