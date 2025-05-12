package net.rewerk.webstore.transport.dto.response.review;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.rewerk.webstore.configuration.pointer.ViewLevel;

import java.util.Map;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonView(ViewLevel.RoleAnonymous.class)
public class LastRatingsDto {
    private Map<Integer, Long> ratings;
    private Integer total;
}
