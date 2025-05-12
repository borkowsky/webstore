package net.rewerk.webstore.repository;

import lombok.NonNull;
import net.rewerk.webstore.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @NonNull
    Page<User> findAll(@NonNull Pageable pageable);

    List<User> findAllByUsernameLikeIgnoreCase(String username);

    Optional<User> getByUsername(String username);

    Boolean existsByEmail(String email);

    Boolean existsByUsername(String username);
}
