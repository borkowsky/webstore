package net.rewerk.webstore.model.dto.response;

import lombok.Getter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class ValidationErrorResponse {
    private final int code;
    private final String message;
    private final String timestamp;
    private final Map<String, List<String>> errors;

    public ValidationErrorResponse(int code, String message) {
        this.code = code;
        this.message = message;
        this.timestamp = String.valueOf(Instant.now());
        this.errors = new HashMap<>();
    }

    public void put(String field, String message) {
        errors.computeIfAbsent(field, k -> new ArrayList<>()).add(message);
    }
}
