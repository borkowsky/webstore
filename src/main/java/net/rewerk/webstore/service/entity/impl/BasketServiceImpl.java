package net.rewerk.webstore.service.entity.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import net.rewerk.webstore.exception.EntityExistsException;
import net.rewerk.webstore.exception.UnprocessableOperation;
import net.rewerk.webstore.model.dto.request.basket.CreateDto;
import net.rewerk.webstore.model.dto.request.basket.MultipleDeleteDto;
import net.rewerk.webstore.model.dto.request.basket.PatchDto;
import net.rewerk.webstore.model.entity.Basket;
import net.rewerk.webstore.model.entity.Category;
import net.rewerk.webstore.model.entity.Product;
import net.rewerk.webstore.model.entity.User;
import net.rewerk.webstore.model.mapper.BasketDtoMapper;
import net.rewerk.webstore.repository.BasketRepository;
import net.rewerk.webstore.service.entity.BasketService;
import net.rewerk.webstore.service.entity.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BasketServiceImpl implements BasketService {
    private final BasketDtoMapper basketDtoMapper;
    private final BasketRepository basketRepository;
    private final ProductService productService;

    @Override
    @Transactional
    public Basket create(CreateDto dto, User user) throws EntityExistsException {
        if (basketRepository.existsByProduct_IdAndUserId(dto.getProduct_id(), user.getId())) {
            throw new EntityExistsException("Basket already exists");
        }
        Product product = productService.findById(dto.getProduct_id());
        Basket basket = basketDtoMapper.createDtoToBasket(dto);
        basket.setProduct(product);
        basket.setUserId(user.getId());
        return basketRepository.save(basket);
    }

    @Override
    @Transactional
    public Basket update(Basket basket, PatchDto dto, User user) {
        if (!Objects.equals(basket.getUserId(), user.getId())) {
            throw new EntityNotFoundException("Basket not found");
        }
        if (dto.getAmount() != null) {
            Integer amountDiff = dto.getAmount().compareTo(basket.getAmount());
            Product product = basket.getProduct();
            if (product.getBalance() < dto.getAmount()) {
                throw new UnprocessableOperation("Products quantity limit exceeded");
            }
            basket.setAmount(dto.getAmount());
            product.setBalance(product.getBalance() - amountDiff);
            productService.update(product);
        }
        return basketRepository.save(basket);
    }

    @Override
    @Transactional
    public void delete(Basket basket, User user) throws EntityNotFoundException {
        if (!Objects.equals(user.getId(), basket.getUserId())) {
            throw new EntityNotFoundException("Basket not found");
        }
        basketRepository.findById(basket.getId())
                .ifPresentOrElse(basketRepository::delete, () -> {
                    throw new EntityNotFoundException("Basket item not found");
                });
    }

    @Override
    @Transactional
    public void deleteAllById(MultipleDeleteDto dto, User user) {
        List<Basket> baskets = basketRepository.findAddByIdInAndUserId(
                List.of(dto.getBasket_ids()), user.getId()
        );
        basketRepository.deleteAllById(baskets.stream()
                .filter(Objects::nonNull)
                .filter(basket -> Objects.equals(basket.getUserId(), user.getId()))
                .map(Basket::getId)
                .collect(Collectors.toList())
        );
    }

    @Override
    @Transactional
    public void deleteAllById(List<Integer> ids) {
        basketRepository.deleteAllById(ids);
    }

    @Override
    public Basket findById(Integer id) throws EntityNotFoundException {
        return basketRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Basket item not found")
        );
    }

    @Override
    public List<Integer> findCategoriesIds(Specification<Basket> specification) {
        return basketRepository.findAll(specification).stream()
                .map(Basket::getProduct)
                .filter(Objects::nonNull)
                .map(Product::getCategory)
                .filter(Objects::nonNull)
                .map(Category::getId)
                .toList();
    }

    @Override
    public Page<Basket> findAll(Pageable pageable) {
        return basketRepository.findAll(pageable);
    }

    @Override
    public Page<Basket> findAll(Specification<Basket> specification, Pageable pageable) {
        return basketRepository.findAll(specification, pageable);
    }

    @Override
    public List<Basket> findAll(Specification<Basket> specification) {
        return basketRepository.findAll(specification);
    }

    @Override
    public List<Basket> findAddByIdsInAndUserId(List<Integer> ids, Integer userId) {
        return basketRepository.findAddByIdInAndUserId(ids, userId);
    }
}
