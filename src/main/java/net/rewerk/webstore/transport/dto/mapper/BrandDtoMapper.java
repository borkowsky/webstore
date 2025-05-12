package net.rewerk.webstore.transport.dto.mapper;

import net.rewerk.webstore.transport.dto.request.brand.CreateDto;
import net.rewerk.webstore.transport.dto.request.brand.PatchDto;
import net.rewerk.webstore.model.entity.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface BrandDtoMapper {
    Brand createDtoToBrand(CreateDto dto);
    Brand patchDtoToBrand(@MappingTarget Brand brand, PatchDto dto);
}
