package net.rewerk.webstore.model.dto.response.exception;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import net.rewerk.webstore.configuration.pointer.ViewLevel;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Getter
@JsonView(ViewLevel.RoleAnonymous.class)
public class EntityNotFoundExceptionResponse {
    private final int code;
    private final String message;
    private final String details;
    private final String timestamp;

    public EntityNotFoundExceptionResponse(String details) {
        this.code = HttpStatus.NOT_FOUND.value();
        this.message = HttpStatus.NOT_FOUND.getReasonPhrase();
        this.details = details;
        this.timestamp = String.valueOf(Instant.now());
    }
}
