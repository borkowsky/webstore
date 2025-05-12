package net.rewerk.webstore.transport.dto.request.payment;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.rewerk.webstore.transport.dto.request.common.SortedRequestParamsDto;

@EqualsAndHashCode(callSuper = true)
@Data
public class SearchDto extends SortedRequestParamsDto {
    private String status;
}
