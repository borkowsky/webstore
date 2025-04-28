package net.rewerk.webstore.repository;

import lombok.NonNull;
import net.rewerk.webstore.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    @NonNull
    Page<User> findAll(@NonNull Pageable pageable);

    Optional<User> getByUsername(String username);

    Optional<User> getByEmail(String email);

    Boolean existsByEmail(String email);

    Boolean existsByUsername(String username);
}
