package net.rewerk.webstore.model.dto.request.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.rewerk.webstore.model.dto.request.common.SortedRequestParamsDto;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchDto extends SortedRequestParamsDto {
    private Integer category_id = null;
}
