package net.rewerk.webstore.model.entity.meta;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.Data;
import net.rewerk.webstore.configuration.pointer.ViewLevel;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.util.Date;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonView(ViewLevel.RoleAnonymous.class)
public class DeletableEntityMeta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Integer id;
    @CreatedDate
    @Column(updatable = false)
    @JsonView(ViewLevel.RoleAdministrator.class)
    private Date createdAt;
    @LastModifiedDate
    @JsonView(ViewLevel.RoleAdministrator.class)
    private Date updatedAt;
}
