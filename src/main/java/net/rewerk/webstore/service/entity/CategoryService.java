package net.rewerk.webstore.service.entity;

import net.rewerk.webstore.model.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface CategoryService {
    Category findById(Integer id);

    Category create(Category category);

    Category update(Category category);

    void delete(Integer id);

    Page<Category> findAll(Pageable pageable);

    Page<Category> findAll(Specification<Category> specification, Pageable pageable);

    List<Category> findAllDistinctById(List<Integer> ids);
}
