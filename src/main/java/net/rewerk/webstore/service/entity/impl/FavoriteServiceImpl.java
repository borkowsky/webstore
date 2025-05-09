package net.rewerk.webstore.service.entity.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import net.rewerk.webstore.exception.EntityExistsException;
import net.rewerk.webstore.model.dto.request.favorite.CreateDto;
import net.rewerk.webstore.model.entity.Category;
import net.rewerk.webstore.model.entity.Favorite;
import net.rewerk.webstore.model.entity.Product;
import net.rewerk.webstore.model.entity.User;
import net.rewerk.webstore.model.mapper.FavoriteDtoMapper;
import net.rewerk.webstore.repository.FavoriteRepository;
import net.rewerk.webstore.service.entity.FavoriteService;
import net.rewerk.webstore.service.entity.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
@Transactional
public class FavoriteServiceImpl implements FavoriteService {
    private final FavoriteDtoMapper favoriteDtoMapper;
    private final FavoriteRepository favoriteRepository;
    private final ProductService productService;

    @Override
    public Favorite create(CreateDto createDto, User user) throws EntityExistsException {
        if (favoriteRepository.existsByUserIdAndProduct_Id(
                user.getId(), createDto.getProduct_id()
        )) {
            throw new EntityExistsException("Favorite already exists");
        }
        Favorite mapped = favoriteDtoMapper.createDtoToFavorite(createDto);
        if (createDto.getProduct_id() != null) {
            Product product = productService.findById(createDto.getProduct_id());
            mapped.setProduct(product);
        }
        mapped.setUserId(user.getId());
        return favoriteRepository.save(mapped);
    }

    @Override
    public Favorite update(Favorite favorite) {
        return favoriteRepository.save(favorite);
    }

    @Override
    public void delete(User user, Favorite favorite) {
        if (!Objects.equals(user.getId(), favorite.getUserId())) {
            throw new EntityNotFoundException("Favorite not found");
        }
        favoriteRepository.delete(favorite);
    }

    @Override
    public Favorite findById(Integer id) {
        return favoriteRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Favorite not found"));
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
