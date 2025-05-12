package net.rewerk.webstore.repository;

import net.rewerk.webstore.model.entity.Product;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {

    @Query("select p.brand.id from Product p where p.category.id = :categoryId")
    List<Integer> findBrandIdsByCategoryId(Integer categoryId);

    List<Product> findAllByRatingGreaterThanOrderByRatingDesc(Double ratingIsGreaterThan, Limit limit);
}
