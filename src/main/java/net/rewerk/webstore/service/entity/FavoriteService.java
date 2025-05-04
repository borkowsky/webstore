package net.rewerk.webstore.service.entity;

import net.rewerk.webstore.model.entity.Favorite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface FavoriteService {
     Favorite create(Favorite favorite);

     Favorite update(Favorite favorite);

     void delete(Integer id);

     Favorite findById(Integer id);

     Page<Favorite> findAll(Pageable pageable);

     Page<Favorite> findAll(Specification<Favorite> specification, Pageable pageable);

     List<Integer> findCategoriesIds(Specification<Favorite> specification);
}
