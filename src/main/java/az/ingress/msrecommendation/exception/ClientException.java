package az.ingress.msrecommendation.exception;

import lombok.Getter;

@Getter
public class ClientException extends RuntimeException {
    private String code;
    private final int status;

    public ClientException(String message, String code, int status) {
        super(message);
        this.code = code;
        this.status = status;
    }
}
