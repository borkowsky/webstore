package net.rewerk.webstore.util;

import net.rewerk.webstore.model.entity.User;
import net.rewerk.webstore.model.entity.meta.EntityMeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.Instant;
import java.util.Date;

public abstract class ServiceUtils {
    public static <T extends EntityMeta> void setDeletionInfo(JpaRepository<T, Integer> repository, Integer id) {
        repository.findById(id).ifPresent(entity -> setDeletionInfo(repository, entity));
    }

    public static <T extends EntityMeta> void setDeletionInfo(JpaRepository<T, Integer> repository, T entity) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        entity.setDeletedAt(Date.from(Instant.now()));
        if (user != null) {
            entity.setDeletedBy(user.getId());
        }
        repository.save(entity);
    }
}
