package net.rewerk.webstore.repository;

import net.rewerk.webstore.model.entity.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Collection;
import java.util.List;

public interface BasketRepository extends JpaRepository<Basket, Integer>, JpaSpecificationExecutor<Basket> {
    boolean existsByProduct_IdAndUserId(Integer productId, Integer userId);

    List<Basket> findAddByIdInAndUserId(Collection<Integer> ids, Integer userId);
}
