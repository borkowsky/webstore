package net.rewerk.webstore.model.dto.response.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import net.rewerk.webstore.model.dto.response.TypedBaseResponseDto;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
public class SinglePayloadResponseDto<T> extends TypedBaseResponseDto<T> {
    private T payload;
}
