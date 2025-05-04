package net.rewerk.webstore.service.entity;

import net.rewerk.webstore.model.entity.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface BrandService {
    Brand getById(Integer id);

    Brand create(Brand brand);

    Brand update(Brand brand);

    void delete(Integer id);

    Page<Brand> findAll(Pageable pageable);

    Page<Brand> findAll(Specification<Brand> specification, Pageable pageable);
}
