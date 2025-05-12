package net.rewerk.webstore.service.entity;

import net.rewerk.webstore.transport.dto.request.basket.CreateDto;
import net.rewerk.webstore.transport.dto.request.basket.MultipleDeleteDto;
import net.rewerk.webstore.transport.dto.request.basket.PatchDto;
import net.rewerk.webstore.model.entity.Basket;
import net.rewerk.webstore.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface BasketService {
    Basket create(CreateDto dto, User user);

    Basket update(Basket basket, PatchDto dto, User user);

    void delete(Basket basket, User user);

    void deleteAllById(MultipleDeleteDto dto, User user);

    void deleteAllById(List<Integer> ids);

    Basket findById(Integer id);

    List<Integer> findCategoriesIds(Specification<Basket> specification);

    Page<Basket> findAll(Pageable pageable);

    Page<Basket> findAll(Specification<Basket> specification, Pageable pageable);

    List<Basket> findAll(Specification<Basket> specification);

    List<Basket> findAddByIdsInAndUserId(List<Integer> ids, Integer userId);
}
