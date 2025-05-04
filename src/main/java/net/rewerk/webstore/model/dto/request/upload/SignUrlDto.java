package net.rewerk.webstore.model.dto.request.upload;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.rewerk.webstore.model.entity.Upload;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUrlDto {
    @NotNull(message = "Filename {validation.common.required}")
    private String filename;
    @NotNull(message = "Mime type {validation.common.required}")
    private String mime;
    @NotNull(message = "Upload type {validation.common.required}")
    private Upload.Type type;
}
