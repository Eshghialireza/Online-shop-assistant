package ir.shop.application.exception;

public class CodeRequestTooSoonException extends RuntimeException {
    public CodeRequestTooSoonException(String message) {
        super(message);
    }
}
