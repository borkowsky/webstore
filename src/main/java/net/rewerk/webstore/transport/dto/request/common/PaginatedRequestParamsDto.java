package net.rewerk.webstore.transport.dto.request.common;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginatedRequestParamsDto {
    @NotNull(message = "Page {validation.common.required}")
    @Min(value = 0, message = "Page {validation.common.min}")
    @Max(value = Integer.MAX_VALUE, message = "Page {validation.common.max}")
    protected Integer page = 0;
    @NotNull(message = "Limit {validation.common.required}")
    @Min(value = 0, message = "Limit {validation.common.min}")
    @Max(value = 500, message = "Limit {validation.common.max}")
    protected Integer limit = 20;
}
