package net.rewerk.webstore.transport.dto.mapper;

import net.rewerk.webstore.transport.dto.request.category.CreateDto;
import net.rewerk.webstore.transport.dto.request.category.PatchDto;
import net.rewerk.webstore.model.entity.Category;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface CategoryDtoMapper {
    Category createDtoToCategory(CreateDto dto);
    Category updateDtoToCategory(@MappingTarget Category category, PatchDto patchDto);
}
