package net.rewerk.webstore.repository;

import lombok.NonNull;
import net.rewerk.webstore.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {
    @NonNull
    Page<Product> findAll(@NonNull Pageable pageable);
}
