package net.rewerk.webstore.model.dto.response.auth;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;
import lombok.experimental.SuperBuilder;
import net.rewerk.webstore.configuration.pointer.ViewLevel;
import net.rewerk.webstore.model.dto.response.BaseResponseDto;
import net.rewerk.webstore.model.entity.User;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@JsonView(ViewLevel.RoleAnonymous.class)
public class AuthenticationResponseDto extends BaseResponseDto {
    private String token;
    private User user;
}
