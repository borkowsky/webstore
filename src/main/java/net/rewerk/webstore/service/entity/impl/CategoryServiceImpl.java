package net.rewerk.webstore.service.entity.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import net.rewerk.webstore.model.dto.request.category.CreateDto;
import net.rewerk.webstore.model.dto.request.category.PatchDto;
import net.rewerk.webstore.model.entity.Category;
import net.rewerk.webstore.model.mapper.CategoryDtoMapper;
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
    private final CategoryDtoMapper categoryDtoMapper;
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;

    @Override
    public Category findById(Integer id) {
        return categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Category not found")
        );
    }

    @Override
    @Transactional
    public Category create(CreateDto dto) {
        Category mapped = categoryDtoMapper.createDtoToCategory(dto);
        if (dto.getCategory_id() != null) {
            categoryRepository.findById(dto.getCategory_id())
                    .ifPresentOrElse(
                            category -> mapped.setCategory_id(category.getId()),
                            () -> {
                                throw new EntityNotFoundException("Parent category was not found");
                            }
                    );
        }
        Category result = categoryRepository.save(mapped);
        writeEvent(eventRepository, String.format(
                "Created new category: %s (ID %d)",
                result.getName(),
                result.getId()
        ));
        return result;
    }

    @Override
    @Transactional
    public Category update(Category category, PatchDto dto) {
        Category mapped = categoryDtoMapper.updateDtoToCategory(category, dto);
        if (dto.getCategory_id() != null) {
            categoryRepository.findById(dto.getCategory_id())
                    .ifPresentOrElse(
                            (parent) -> mapped.setCategory_id(parent.getId()),
                            () -> {
                                throw new EntityNotFoundException("Parent category was not found");
                            });
        }
        Category result = categoryRepository.save(mapped);
        writeEvent(eventRepository, String.format(
                "Updated category: %s (ID %d)",
                result.getName(),
                result.getId()
        ));
        return result;
    }

    @Override
    @Transactional
    public void delete(Category category) {
        ServiceUtils.setDeletionInfo(categoryRepository, category);
        categoryRepository.deleteById(category.getId());
        writeEvent(eventRepository, String.format(
                "Deleted category: %s (ID %d)",
                category.getName(),
                category.getId()
        ));

    }

    @Override
    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public Page<Category> findAll(Specification<Category> specification, Pageable pageable) {
        return categoryRepository.findAll(specification, pageable);
    }

    @Override
    public List<Category> findAllDistinctById(List<Integer> ids) {
        return categoryRepository.findDistinctByIdIn(ids);
    }
}
