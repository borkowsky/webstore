package net.rewerk.webstore.model.dto.response.upload;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.rewerk.webstore.configuration.pointer.ViewLevel;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonView(ViewLevel.RoleAdministrator.class)
public class SignUrlResponseDto {
    private String uploadURL;
    private String publicURL;
}
