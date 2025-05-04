package net.rewerk.webstore.repository;

import net.rewerk.webstore.model.entity.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BasketRepository extends JpaRepository<Basket, Integer>, JpaSpecificationExecutor<Basket> {}
