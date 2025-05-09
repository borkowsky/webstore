package net.rewerk.webstore.model.dto.response.exception;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import net.rewerk.webstore.configuration.pointer.ViewLevel;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Getter
@JsonView(ViewLevel.RoleAnonymous.class)
public class UnprocessableExceptionResponse {
    private final int code;
    private final String message;
    private final String details;
    private final String timestamp;

    public UnprocessableExceptionResponse(String details) {
        this.code = HttpStatus.UNPROCESSABLE_ENTITY.value();
        this.message = HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase();
        this.details = details;
        this.timestamp = String.valueOf(Instant.now());
    }
}
