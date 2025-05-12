package net.rewerk.webstore.transport.dto.response.common;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;
import lombok.experimental.SuperBuilder;
import net.rewerk.webstore.configuration.pointer.ViewLevel;
import net.rewerk.webstore.transport.dto.response.TypedBaseResponseDto;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@JsonView(ViewLevel.RoleAnonymous.class)
public class PaginatedPayloadResponseDto<T> extends TypedBaseResponseDto<T> {
    private List<T> payload;
    private Long total;
    private Integer page;
    private Integer pages;
}
