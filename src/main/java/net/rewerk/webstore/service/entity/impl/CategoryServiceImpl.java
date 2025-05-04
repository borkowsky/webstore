package net.rewerk.webstore.service.entity.impl;

import lombok.RequiredArgsConstructor;
import net.rewerk.webstore.model.entity.Category;
import net.rewerk.webstore.repository.CategoryRepository;
import net.rewerk.webstore.repository.EventRepository;
import net.rewerk.webstore.service.EventWritingService;
import net.rewerk.webstore.service.entity.CategoryService;
import net.rewerk.webstore.util.ServiceUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl extends EventWritingService implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;

    @Override
    public Category findById(Integer id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Category create(Category category) {
        Category result = categoryRepository.save(category);
        writeEvent(eventRepository, String.format(
                "Created new category: %s (ID %d)",
                result.getName(),
                result.getId()
        ));
        return result;
    }

    @Override
    @Transactional
    public Category update(Category category) {
        Category result = categoryRepository.save(category);
        writeEvent(eventRepository, String.format(
                "Updated category: %s (ID %d)",
                result.getName(),
                result.getId()
        ));
        return result;
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Category category = findById(id);
        if (category != null) {
            ServiceUtils.setDeletionInfo(categoryRepository, category);
            categoryRepository.deleteById(id);
            writeEvent(eventRepository, String.format(
                    "Deleted category: %s (ID %d)",
                    category.getName(),
                    category.getId()
            ));

        }
    }

    @Override
    public Page<Category> findAll(Pageable pageable) {
        Page<Category> result;
        try {
            result = categoryRepository.findAll(pageable);
        } catch (Exception e) {
            e.printStackTrace();
            result = Page.empty();
        }
        return result;
    }

    @Override
    public Page<Category> findAll(Specification<Category> specification, Pageable pageable) {
        Page<Category> result;
        try {
            result = categoryRepository.findAll(specification, pageable);
        } catch (Exception e) {
            e.printStackTrace();
            result = Page.empty();
        }
        return result;
    }

    @Override
    public List<Category> findAllDistinctById(List<Integer> ids) {
        return categoryRepository.findDistinctByIdIn(ids);
    }
}
