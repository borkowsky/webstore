package net.rewerk.webstore.repository;

import lombok.NonNull;
import net.rewerk.webstore.model.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, Integer>, JpaSpecificationExecutor<Brand> {
    @NonNull
    Optional<Brand> findById(@NonNull Integer id);

    boolean existsByName(String name);
}
