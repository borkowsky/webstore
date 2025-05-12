package net.rewerk.webstore.transport.dto.request.brand;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatchDto {
    @Size(
            min = 2,
            max = 256,
            message = "Name {validation.common.length}"
    )
    private String name;
    @NotEmpty(message = "Image {validation.common.empty}")
    private String image;
}
