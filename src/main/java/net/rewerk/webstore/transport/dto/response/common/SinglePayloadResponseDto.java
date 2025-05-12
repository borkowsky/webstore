package net.rewerk.webstore.transport.dto.response.common;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import net.rewerk.webstore.configuration.pointer.ViewLevel;
import net.rewerk.webstore.transport.dto.response.TypedBaseResponseDto;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@JsonView(ViewLevel.RoleAnonymous.class)
public class SinglePayloadResponseDto<T> extends TypedBaseResponseDto<T> {
    private T payload;
}
