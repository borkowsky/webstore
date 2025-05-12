package net.rewerk.webstore.service.entity;

import net.rewerk.webstore.transport.dto.request.category.CreateDto;
import net.rewerk.webstore.transport.dto.request.category.PatchDto;
import net.rewerk.webstore.model.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface CategoryService {
    Category findById(Integer id);

    Category create(CreateDto dto);

    Category update(Category category, PatchDto dto);

    void delete(Category category);

    Page<Category> findAll(Pageable pageable);

    Page<Category> findAll(Specification<Category> specification, Pageable pageable);

    List<Category> findAllDistinctById(List<Integer> ids);
}
