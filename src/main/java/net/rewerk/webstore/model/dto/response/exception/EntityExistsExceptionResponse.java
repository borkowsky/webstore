package net.rewerk.webstore.model.dto.response.exception;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import net.rewerk.webstore.configuration.pointer.ViewLevel;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Getter
@JsonView(ViewLevel.RoleAnonymous.class)
public class EntityExistsExceptionResponse {
    private final int code;
    private final String message;
    private final String details;
    private final String timestamp;

    public EntityExistsExceptionResponse(String details) {
        this.code = HttpStatus.CONFLICT.value();
        this.message = HttpStatus.CONFLICT.getReasonPhrase();
        this.details = details;
        this.timestamp = String.valueOf(Instant.now());
    }
}
