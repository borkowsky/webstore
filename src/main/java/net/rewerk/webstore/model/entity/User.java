package net.rewerk.webstore.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.*;
import net.rewerk.webstore.configuration.pointer.ViewLevel;
import net.rewerk.webstore.model.entity.meta.EntityMeta;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLOrdinalEnumJdbcType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@JsonView(ViewLevel.RoleUser.class)
public class User extends EntityMeta implements UserDetails {
    public enum Role {
        USER,
        MANAGER,
        ADMINISTRATOR
    }

    @Column(
            unique = true,
            nullable = false
    )
    private String username;
    @Column(
            unique = true,
            nullable = false
    )
    private String email;
    @Column(nullable = false)
    @JsonIgnore
    private String password;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLOrdinalEnumJdbcType.class)
    @JsonView(ViewLevel.RoleAdministrator.class)
    private Role role;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}
