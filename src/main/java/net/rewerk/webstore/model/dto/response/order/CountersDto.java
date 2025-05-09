package net.rewerk.webstore.model.dto.response.order;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.rewerk.webstore.configuration.pointer.ViewLevel;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonView(ViewLevel.RoleUser.class)
public class CountersDto {
    private Long active;
    private Long completed;
}
