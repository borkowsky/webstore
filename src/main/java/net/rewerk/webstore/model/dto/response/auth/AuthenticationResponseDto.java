package net.rewerk.webstore.model.dto.response.auth;

import lombok.*;
import lombok.experimental.SuperBuilder;
import net.rewerk.webstore.model.dto.response.BaseResponseDto;
import net.rewerk.webstore.model.entity.User;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class AuthenticationResponseDto extends BaseResponseDto {
    private String token;
    private User user;
}
