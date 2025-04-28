package net.rewerk.webstore.model.dto.response.common;

import lombok.*;
import lombok.experimental.SuperBuilder;
import net.rewerk.webstore.model.dto.response.TypedBaseResponseDto;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class PaginatedPayloadResponseDto<T> extends TypedBaseResponseDto<T> {
    private List<T> payload;
    private Integer size;
    private Integer page;
    private Integer pages;
}
