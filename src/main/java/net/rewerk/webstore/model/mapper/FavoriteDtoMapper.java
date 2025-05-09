package net.rewerk.webstore.model.mapper;

import net.rewerk.webstore.model.dto.request.favorite.CreateDto;
import net.rewerk.webstore.model.entity.Favorite;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface FavoriteDtoMapper {
    Favorite createDtoToFavorite (CreateDto dto);
}
