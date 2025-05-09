package net.rewerk.webstore.model.dto.request.payment;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.rewerk.webstore.model.dto.request.common.SortedRequestParamsDto;

@EqualsAndHashCode(callSuper = true)
@Data
public class SearchDto extends SortedRequestParamsDto {
    private String status;
}
