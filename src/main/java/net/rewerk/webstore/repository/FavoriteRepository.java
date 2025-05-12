package net.rewerk.webstore.repository;

import net.rewerk.webstore.model.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Integer>, JpaSpecificationExecutor<Favorite> {
    boolean existsByUserIdAndProduct_Id(Integer userId, Integer productId);
}
