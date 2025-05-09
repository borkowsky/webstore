package net.rewerk.webstore.model.dto.response.exception;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import net.rewerk.webstore.configuration.pointer.ViewLevel;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Getter
@JsonView(ViewLevel.RoleAnonymous.class)
public class InternalServerErrorExceptionResponse {
    private final int code;
    private final String message;
    private final String details;
    private final String timestamp;

    public InternalServerErrorExceptionResponse(String details) {
        this.code = HttpStatus.INTERNAL_SERVER_ERROR.value();
        this.message = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
        this.details = details;
        this.timestamp = String.valueOf(Instant.now());
    }
}
