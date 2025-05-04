package net.rewerk.webstore.model.dto.request.brand;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.rewerk.webstore.model.dto.request.common.SortedRequestParamsDto;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchDto extends SortedRequestParamsDto {
    private String name_query;
}
