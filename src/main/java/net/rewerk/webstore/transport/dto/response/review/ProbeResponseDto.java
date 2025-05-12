package net.rewerk.webstore.transport.dto.response.review;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.rewerk.webstore.configuration.pointer.ViewLevel;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonView(ViewLevel.RoleUser.class)
public class ProbeResponseDto {
    private Integer order_id;
    private Integer product_id;
    private Boolean allowed;
}
