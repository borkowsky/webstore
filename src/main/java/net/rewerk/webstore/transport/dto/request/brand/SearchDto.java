package net.rewerk.webstore.transport.dto.request.brand;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.rewerk.webstore.transport.dto.request.common.SortedRequestParamsDto;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchDto extends SortedRequestParamsDto {
    private String name_query;
    private Integer product_category_id;
}
