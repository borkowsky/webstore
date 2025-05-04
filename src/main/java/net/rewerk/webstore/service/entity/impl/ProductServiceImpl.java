package net.rewerk.webstore.service.entity.impl;

import lombok.RequiredArgsConstructor;
import net.rewerk.webstore.model.entity.Product;
import net.rewerk.webstore.repository.EventRepository;
import net.rewerk.webstore.repository.ProductRepository;
import net.rewerk.webstore.service.EventWritingService;
import net.rewerk.webstore.service.entity.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl extends EventWritingService implements ProductService {
    private final ProductRepository productRepository;
    private final EventRepository eventRepository;

    @Override
    public boolean existsById(Integer id) {
        return productRepository.existsById(id);
    }

    @Override
    public Product findById(Integer id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        try {
            return productRepository.findAll(pageable);
        } catch (Exception e) {
            e.printStackTrace();
            return Page.empty();
        }
    }

    @Override
    public Page<Product> findAll(Specification<Product> specification, Pageable pageable) {
        try {
            return productRepository.findAll(specification, pageable);
        } catch (Exception e) {
            e.printStackTrace();
            return Page.empty();
        }
    }

    @Override
    @Transactional
    public Product create(Product product) {
        Product result = productRepository.save(product);
        writeEvent(eventRepository, String.format("Created product: %s (ID %d)", product.getName(), product.getId()));
        return result;
    }

    @Override
    public Product update(Product product) {
        Product result = productRepository.save(product);
        writeEvent(eventRepository, String.format("Updated product: %s (%d)", product.getName(), product.getId()));
        return result;
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        productRepository.findById(id).ifPresent(product -> {
            productRepository.deleteById(id);
            writeEvent(eventRepository, String.format("Deleted product: %s (%d)", product.getName(), product.getId()));
        });
    }
}
