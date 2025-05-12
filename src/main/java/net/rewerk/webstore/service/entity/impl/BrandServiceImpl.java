package net.rewerk.webstore.service.entity.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.rewerk.webstore.exception.EntityExistsException;
import net.rewerk.webstore.transport.dto.request.brand.CreateDto;
import net.rewerk.webstore.transport.dto.request.brand.PatchDto;
import net.rewerk.webstore.model.entity.Brand;
import net.rewerk.webstore.model.entity.Upload;
import net.rewerk.webstore.transport.dto.mapper.BrandDtoMapper;
import net.rewerk.webstore.repository.BrandRepository;
import net.rewerk.webstore.service.UploadService;
import net.rewerk.webstore.service.entity.BrandService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {
    private final BrandDtoMapper brandDtoMapper;
    private final BrandRepository brandRepository;
    private final UploadService uploadService;

    @Override
    public Brand findById(Integer id) {
        return brandRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Brand not found"));
    }

    @Override
    @Transactional
    public Brand create(@NonNull Brand brand) {
        if (brandRepository.existsByName(brand.getName())) {
            throw new EntityExistsException(
                    "Brand with name %s already exists".formatted(brand.getName())
            );
        }
        return brandRepository.save(brand);
    }

    @Override
    @Transactional
    public Brand create(CreateDto dto) {
        Brand brand = brandDtoMapper.createDtoToBrand(dto);
        return this.create(brand);
    }

    @Override
    @Transactional
    public Brand update(Brand brand) {
        return brandRepository.save(brand);
    }

    @Override
    @Transactional
    public Brand update(Brand brand, PatchDto dto) {
        if (!Objects.equals(dto.getImage(), brand.getImage())) {
            uploadService.deleteObject(brand.getImage(), Upload.Type.BRAND_IMAGE);
        }
        Brand result = brandDtoMapper.patchDtoToBrand(brand, dto);
        return this.update(result);
    }

    @Override
    @Transactional
    public void delete(Brand brand) {
        if (brand.getImage() != null) {
            uploadService.deleteObject(brand.getImage(), Upload.Type.BRAND_IMAGE);
        }
        brandRepository.delete(brand);
    }

    @Override
    public Page<Brand> findAll(Pageable pageable) {
        return brandRepository.findAll(pageable);
    }

    @Override
    public Page<Brand> findAll(Specification<Brand> specification, Pageable pageable) {
        return brandRepository.findAll(specification, pageable);
    }

    @Override
    public Page<Brand> findAllByProductCategoryId(Integer productCategoryId) {
        List<Brand> result = brandRepository.findDistinctByProductCategoryId(productCategoryId);
        return new PageImpl<>(result, PageRequest.of(0, result.size() + 1), result.size());
    }
}
