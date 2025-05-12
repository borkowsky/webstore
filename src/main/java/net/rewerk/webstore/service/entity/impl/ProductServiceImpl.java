package net.rewerk.webstore.service.entity.impl;

import lombok.RequiredArgsConstructor;
import net.rewerk.webstore.exception.OperationInterruptedException;
import net.rewerk.webstore.transport.dto.request.product.PatchDto;
import net.rewerk.webstore.model.entity.Product;
import net.rewerk.webstore.model.entity.Upload;
import net.rewerk.webstore.transport.dto.mapper.ProductDtoMapper;
import net.rewerk.webstore.repository.EventRepository;
import net.rewerk.webstore.repository.ProductRepository;
import net.rewerk.webstore.service.EventWritingService;
import net.rewerk.webstore.service.UploadService;
import net.rewerk.webstore.service.entity.BrandService;
import net.rewerk.webstore.service.entity.CategoryService;
import net.rewerk.webstore.service.entity.ProductService;
import net.rewerk.webstore.util.CommonUtils;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl extends EventWritingService implements ProductService {
    private final ProductDtoMapper productDtoMapper;
    private final ProductRepository productRepository;
    private final EventRepository eventRepository;
    private final CategoryService categoryService;
    private final BrandService brandService;
    private final UploadService uploadService;

    @Override
    public Product findById(Integer id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Page<Product> findAll(Specification<Product> specification, Pageable pageable) {
        return productRepository.findAll(specification, pageable);
    }

    @Override
    public List<Product> findPopular() {
        return productRepository
                .findAllByRatingGreaterThanOrderByRatingDesc(0.0, Limit.of(20));
    }

    @Override
    @Transactional
    public Product create(Product product) {
        Product result = productRepository.save(product);
        writeEvent(eventRepository, String.format("Created product: %s (ID %d)", product.getName(), product.getId()));
        return result;
    }

    @Override
    @Transactional
    public Product update(Product product, PatchDto patchDto) {
        Product mapped = productDtoMapper.updateProduct(product, patchDto);
        if (patchDto.getBrand_id() != null) {
            mapped.setBrand(brandService.findById(patchDto.getBrand_id()));
        }
        if (patchDto.getImages() != null) {
            List<String> imagesToDelete = CommonUtils.lostItemsInList(
                    product.getImages(), Arrays.asList(patchDto.getImages())
            );
            if (!imagesToDelete.isEmpty()) {
                try {
                    uploadService.deleteObjects(imagesToDelete, Upload.Type.PRODUCT_IMAGE).join();
                } catch (InterruptedException e) {
                    throw new OperationInterruptedException("Failed to delete not necessary product images");
                }
            }
        }
        if (patchDto.getCategory_id() != null) {
            product.setCategory(categoryService.findById(patchDto.getCategory_id()));
        }
        Product result = productRepository.save(product);
        writeEvent(eventRepository, String.format("Updated product: %s (%d)", product.getName(), product.getId()));
        return result;
    }

    @Override
    public Product update(Product product) {
        return productRepository.save(product);
    }

    @Override
    @Transactional
    public void delete(Product product) {
        try {
            productRepository.deleteById(product.getId());
            if (product.getImages() != null && !product.getImages().isEmpty()) {
                uploadService.deleteObjects(product.getImages(), Upload.Type.PRODUCT_IMAGE).join();
            }
        } catch (InterruptedException e) {
            throw new OperationInterruptedException("Failed to delete product images");
        }
        writeEvent(eventRepository, String.format("Deleted product: %s (%d)", product.getName(), product.getId()));
    }
}
