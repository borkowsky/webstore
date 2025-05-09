package net.rewerk.webstore.exception;

public class UnprocessableOperation extends RuntimeException {
    public UnprocessableOperation(String message) {
        super(message);
    }
}
