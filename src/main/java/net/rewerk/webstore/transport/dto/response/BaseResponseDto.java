package net.rewerk.webstore.transport.dto.response;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class BaseResponseDto {
    private Integer code;
    private String message;
}
