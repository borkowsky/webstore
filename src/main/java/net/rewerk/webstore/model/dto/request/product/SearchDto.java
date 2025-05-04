package net.rewerk.webstore.model.dto.request.product;

import lombok.*;
import net.rewerk.webstore.model.dto.request.common.SortedRequestParamsDto;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchDto extends SortedRequestParamsDto {
    private Integer category_id = -1;
}
