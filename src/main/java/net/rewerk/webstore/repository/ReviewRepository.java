package net.rewerk.webstore.repository;

import net.rewerk.webstore.model.entity.Review;
import net.rewerk.webstore.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer>, JpaSpecificationExecutor<Review> {
    boolean existsByOrderIdAndProduct_IdAndUser(Integer orderId, Integer productId, User user);

    @Query(value = "select r.images from reviews r where r.product_id = :productId limit 15", nativeQuery = true)
    List<List<String>> findAllImagesByProductId(Integer productId);

    @Query(value = "select r.rating from reviews r where r.product_id = :productId order by id desc limit 20", nativeQuery = true)
    List<Integer> findLastRatingsByProductId(Integer productId);
}
