package net.rewerk.webstore.transport.dto.request.order;

import jakarta.validation.constraints.Positive;
import lombok.*;
import net.rewerk.webstore.transport.dto.request.common.SortedRequestParamsDto;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchDto extends SortedRequestParamsDto implements Cloneable {
    @Positive(message = "User ID {validation.common.positive}")
    private Integer user_id; // accept userId value
    private String type; // accept String values: active, completed

    @Override
    public SearchDto clone() {
        try {
            return (SearchDto) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
