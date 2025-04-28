package net.rewerk.webstore.model.dto.request.upload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MultipleDeletionDto {
    @NotNull(message = "Filenames {validation.common.required}")
    @Size(
            min = 1,
            max = 16,
            message = "Filenames array {validation.common.range} of {min}-{max}"
    )
    private String[] filenames;
}
