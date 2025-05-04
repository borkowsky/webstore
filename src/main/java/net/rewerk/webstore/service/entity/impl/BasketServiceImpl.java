package net.rewerk.webstore.service.entity.impl;

import lombok.RequiredArgsConstructor;
import net.rewerk.webstore.exception.EntityExistsException;
import net.rewerk.webstore.model.entity.Basket;
import net.rewerk.webstore.model.specification.BasketSpecification;
import net.rewerk.webstore.repository.BasketRepository;
import net.rewerk.webstore.service.entity.BasketService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BasketServiceImpl implements BasketService {
    private final BasketRepository basketRepository;

    @Override
    public Basket create(Basket basket) {
        if (basketRepository.exists(BasketSpecification.getSpecification(
                basket.getUser(),
                basket.getProduct()
        ))) {
            throw new EntityExistsException("Basket already exists");
        }
        return basketRepository.save(basket);
    }

    @Override
    public Basket update(Basket basket) {
        return basketRepository.save(basket);
    }

    @Override
    public void delete(Basket basket) {
        basketRepository.delete(basket);
    }

    @Override
    public Page<Basket> findAll(Pageable pageable) {
        return basketRepository.findAll(pageable);
    }

    @Override
    public Page<Basket> findAll(Specification<Basket> specification, Pageable pageable) {
        return basketRepository.findAll(specification, pageable);
    }
}
