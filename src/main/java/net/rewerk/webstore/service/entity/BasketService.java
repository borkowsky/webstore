package net.rewerk.webstore.service.entity;

import net.rewerk.webstore.model.entity.Basket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface BasketService {
    Basket create(Basket basket);

    Basket update(Basket basket);

    void delete(Basket basket);

    Page<Basket> findAll(Pageable pageable);

    Page<Basket> findAll(Specification<Basket> specification, Pageable pageable);
}
