package net.rewerk.webstore.repository;

import lombok.NonNull;
import net.rewerk.webstore.model.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer>, JpaSpecificationExecutor<Brand> {
    @NonNull
    Optional<Brand> findById(@NonNull Integer id);

    boolean existsByName(String name);

    @Query("select distinct p.brand from Product p where p.category.id = :categoryId")
    List<Brand> findDistinctByProductCategoryId(Integer categoryId);
}
