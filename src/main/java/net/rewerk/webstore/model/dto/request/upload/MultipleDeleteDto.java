package net.rewerk.webstore.model.dto.request.upload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.rewerk.webstore.model.entity.Upload;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MultipleDeleteDto {
    @NotNull(message = "Filenames {validation.common.required}")
    @Size(
            min = 1,
            max = 16,
            message = "Filenames array {validation.common.range} of {min}-{max}"
    )
    private String[] filenames;
    @NotNull(message = "Upload type {validation.common.required}")
    private Upload.Type type;
}
