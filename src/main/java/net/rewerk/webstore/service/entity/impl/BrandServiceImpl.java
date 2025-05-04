package net.rewerk.webstore.service.entity.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.rewerk.webstore.exception.EntityExistsException;
import net.rewerk.webstore.model.entity.Brand;
import net.rewerk.webstore.repository.BrandRepository;
import net.rewerk.webstore.service.entity.BrandService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;

    @Override
    public Brand getById(Integer id) {
        return brandRepository.findById(id).orElse(null);
    }

    @Override
    public Brand create(@NonNull Brand brand) {
        if (brandRepository.existsByName(brand.getName())) {
            throw new EntityExistsException(
                    "Brand with name %s already exists".formatted(brand.getName())
            );
        }
        return brandRepository.save(brand);
    }

    @Override
    public Brand update(Brand brand) {
        return brandRepository.save(brand);
    }

    @Override
    public void delete(Integer id) {
        brandRepository.deleteById(id);
    }

    @Override
    public Page<Brand> findAll(Pageable pageable) {
        return brandRepository.findAll(pageable);
    }

    @Override
    public Page<Brand> findAll(Specification<Brand> specification, Pageable pageable) {
        return brandRepository.findAll(specification, pageable);
    }
}
