package net.rewerk.webstore.service.entity;

import net.rewerk.webstore.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface ProductService {
    Product findById(Integer id);

    Page<Product> findAll(Pageable pageable);

    Page<Product> findAll(Specification<Product> specification, Pageable pageable);

    Product create(Product product);

    Product update(Product product);

    void delete(Integer id);
}
