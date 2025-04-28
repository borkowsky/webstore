package net.rewerk.webstore.model.dto.request.category;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatchDto {
    @Size(
            min = 4,
            max = 64,
            message = "Name {validation.common.length}"
    )
    private String name;
    private String description;
    @Size(
            min = 2,
            max = 64,
            message = "Icon {validation.common.length}"
    )
    private String icon;
    private Integer category_id;
    private Boolean enabled;
}
