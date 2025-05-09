package net.rewerk.webstore.model.mapper;

import net.rewerk.webstore.model.dto.request.address.CreateDto;
import net.rewerk.webstore.model.dto.request.address.PatchDto;
import net.rewerk.webstore.model.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface AddressDtoMapper {
    Address createDtoToAddress(CreateDto dto);

    Address patchDtoToAddress(@MappingTarget Address address, PatchDto dto);
}
