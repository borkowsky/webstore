package net.rewerk.webstore.model.dto.request.upload;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUrlDto {
    @NotNull(message = "Filename {validation.common.required}")
    private String filename;
    @NotNull(message = "Mime type {validation.common.required}")
    private String mime;
}
