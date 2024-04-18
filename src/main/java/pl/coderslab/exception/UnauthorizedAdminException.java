package pl.coderslab.exception;

public class UnauthorizedAdminException extends RuntimeException{
    public UnauthorizedAdminException(String message) {
        super(message);
    }
}
