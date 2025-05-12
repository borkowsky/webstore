package net.rewerk.webstore.transport.dto.request.brand;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateDto {
    @NotNull(message = "Name {validation.common.required}")
    @Size(
            min = 2,
            max = 256,
            message = "Name {validation.common.length}"
    )
    private String name;
    @NotNull(message = "Image {validation.common.required}")
    @NotEmpty(message = "Image {validation.common.empty}")
    private String image;
}
