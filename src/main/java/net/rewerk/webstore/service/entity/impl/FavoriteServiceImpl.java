package net.rewerk.webstore.service.entity.impl;

import lombok.RequiredArgsConstructor;
import net.rewerk.webstore.exception.EntityExistsException;
import net.rewerk.webstore.model.entity.Category;
import net.rewerk.webstore.model.entity.Favorite;
import net.rewerk.webstore.model.entity.Product;
import net.rewerk.webstore.model.specification.FavoriteSpecification;
import net.rewerk.webstore.repository.FavoriteRepository;
import net.rewerk.webstore.service.entity.FavoriteService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class FavoriteServiceImpl implements FavoriteService {
    private final FavoriteRepository favoriteRepository;

    @Override
    public Favorite create(Favorite favorite) {
        if (favoriteRepository.exists(
                FavoriteSpecification.getSpecification(favorite.getUser_id(), favorite.getProduct()))
        ) {
            throw new EntityExistsException("Favorite already exists");
        }
        return favoriteRepository.save(favorite);
    }

    @Override
    public Favorite update(Favorite favorite) {
        return favoriteRepository.save(favorite);
    }

    @Override
    public void delete(Integer id) {
        favoriteRepository.deleteById(id);
    }

    @Override
    public Favorite findById(Integer id) {
        return favoriteRepository.findById(id).orElse(null);
    }

    @Override
    public Favorite findByUserIdAndProductId(Integer userId, Integer productId) {
        return favoriteRepository.findOne(FavoriteSpecification.getSpecification(
                userId,
                productId
        )).orElse(null);
    }

    @Override
    public Page<Favorite> findAll(Pageable pageable) {
        return favoriteRepository.findAll(pageable);
    }

    @Override
    public Page<Favorite> findAll(Specification<Favorite> specification, Pageable pageable) {
        return favoriteRepository.findAll(specification, pageable);
    }

    @Override
    public List<Integer> findCategoriesIds(Specification<Favorite> specification) {
        return favoriteRepository.findAll(specification).stream()
                .map(Favorite::getProduct)
                .filter(Objects::nonNull)
                .map(Product::getCategory)
                .filter(Objects::nonNull)
                .map(Category::getId).toList();
    }
}
