package net.rewerk.webstore.transport.dto.mapper;

import net.rewerk.webstore.transport.dto.request.basket.CreateDto;
import net.rewerk.webstore.model.entity.Basket;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface BasketDtoMapper {
    Basket createDtoToBasket(CreateDto dto);
}
