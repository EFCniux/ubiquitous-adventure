package es.niux.efc.demoapp.domain.exception;

public class KeywordQueryException extends Exception {
    public KeywordQueryException(String message) {
        super(message);
    }

    public KeywordQueryException(String message, Throwable cause) {
        super(message, cause);
    }

    public KeywordQueryException(Throwable cause) {
        super(cause);
    }
}
