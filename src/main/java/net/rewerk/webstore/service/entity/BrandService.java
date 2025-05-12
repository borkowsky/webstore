package net.rewerk.webstore.service.entity;

import net.rewerk.webstore.transport.dto.request.brand.CreateDto;
import net.rewerk.webstore.transport.dto.request.brand.PatchDto;
import net.rewerk.webstore.model.entity.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface BrandService {
    Brand findById(Integer id);

    Brand create(Brand brand);

    Brand create(CreateDto dto);

    Brand update(Brand brand);

    Brand update(Brand brand, PatchDto dto);

    void delete(Brand brand);

    Page<Brand> findAll(Pageable pageable);

    Page<Brand> findAll(Specification<Brand> specification, Pageable pageable);

    Page<Brand> findAllByProductCategoryId(Integer productCategoryId);
}
