package net.rewerk.webstore.model.dto.request.order;

import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.rewerk.webstore.model.dto.request.common.SortedRequestParamsDto;

@EqualsAndHashCode(callSuper = true)
@Data
public class SearchDto extends SortedRequestParamsDto {
    @Positive(message = "User ID {validation.common.positive}")
    private Integer user_id; // accept userId value
    private String type; // accept String values: active, completed
}
