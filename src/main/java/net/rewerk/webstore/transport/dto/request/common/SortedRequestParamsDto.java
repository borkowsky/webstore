package net.rewerk.webstore.transport.dto.request.common;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SortedRequestParamsDto extends PaginatedRequestParamsDto {
    @NotNull(message = "Sort {validation.common.required}")
    private String sort = "id";
    @NotNull(message = "Order {validation.common.required}")
    private String order = "asc";
}
