package net.rewerk.webstore.model.dto.request.auth;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginDto {
    @NotNull(message = "Username {validation.common.required}")
    @Size(
            min = 4,
            max = 12,
            message = "Username {validation.common.length}"
    )
    private String username;
    @NotNull(message = "Password {validation.common.required}")
    @Size(
            min = 6,
            max = 128,
            message = "Password {validation.common.length}"
    )
    private String password;
}
