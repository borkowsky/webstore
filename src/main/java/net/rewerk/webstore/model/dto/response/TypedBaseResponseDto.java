package net.rewerk.webstore.model.dto.response;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class TypedBaseResponseDto<T> {
    private Integer code;
    private String message;
}
