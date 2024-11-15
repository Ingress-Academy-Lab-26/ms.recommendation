package az.ingress.msrecommendation.exception;

import lombok.Getter;

@Getter
public class QueueException extends RuntimeException {
    private String code;

    public QueueException(String message, String code) {
        super(message);
        this.code = code;
    }
}
