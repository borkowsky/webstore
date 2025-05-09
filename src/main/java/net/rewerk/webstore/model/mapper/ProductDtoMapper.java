package net.rewerk.webstore.model.mapper;

import net.rewerk.webstore.model.dto.request.product.CreateDto;
import net.rewerk.webstore.model.dto.request.product.PatchDto;
import net.rewerk.webstore.model.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ProductDtoMapper {
    Product createProduct(CreateDto createDto);
    Product updateProduct(@MappingTarget Product product, PatchDto patchDto);
}
