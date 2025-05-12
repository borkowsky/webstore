package net.rewerk.webstore.transport.dto.request.upload;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.rewerk.webstore.model.entity.Upload;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteDto {
    @NotNull(message = "Filename {validation.common.required}")
    private String filename;
    @NotNull(message = "Type {validation.common.required}")
    private Upload.Type type;
}
