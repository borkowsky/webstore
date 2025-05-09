package net.rewerk.webstore.model.dto.response.exception;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import net.rewerk.webstore.configuration.pointer.ViewLevel;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@JsonView(ViewLevel.RoleAnonymous.class)
public class ValidationExceptionResponse {
    private final int code;
    private final String message;
    private final String timestamp;
    private final Map<String, List<String>> errors;

    public ValidationExceptionResponse(int code, String message) {
        this.code = code;
        this.message = message;
        this.timestamp = String.valueOf(Instant.now());
        this.errors = new HashMap<>();
    }

    public void put(String field, String message) {
        errors.computeIfAbsent(field, k -> new ArrayList<>()).add(message);
    }
}
