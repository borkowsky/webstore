package net.rewerk.webstore.transport.dto.request.review;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.rewerk.webstore.transport.dto.request.common.SortedRequestParamsDto;

@EqualsAndHashCode(callSuper = true)
@Data
public class SearchDto extends SortedRequestParamsDto {
    @NotNull(message = "Product ID {validation.common.required}")
    private Integer product_id;
    private Integer user_id;
}