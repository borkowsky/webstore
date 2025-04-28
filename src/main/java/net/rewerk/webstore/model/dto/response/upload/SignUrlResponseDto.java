package net.rewerk.webstore.model.dto.response.upload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUrlResponseDto {
    private String uploadURL;
    private String publicURL;
}
