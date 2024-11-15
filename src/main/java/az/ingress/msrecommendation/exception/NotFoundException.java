package az.ingress.msrecommendation.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {
    private String code;

    public NotFoundException(String message, String code) {
        super(message);
        this.code = code;
    }
}
