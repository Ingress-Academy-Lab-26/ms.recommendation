package az.ingress.msrecommendation.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum ExceptionConstants {
    CLIENT_ERROR("CLIENT_EXCEPTION", "Exception from client"),
    UNEXPECTED_EXCEPTION("UNEXPECTED_EXCEPTION", "Unexpected exception occurred"),
    ACCESS_DENIED("ACCESS_DENIED", "Access denied"),
    RECOMMENDATION_NOT_FOUND("RECOMMENDATION_NOT_FOUND_EXCEPTION", "Recommendation not found"),
    HTTP_REQUEST_METHOD_NOT_SUPPORTED_EXCEPTION("METHOD_NOT_ALLOWED", "HTTP request method not supported"),
    METHOD_ARGUMENT_NOT_VALID_EXCEPTION("VALIDATION_ERROR", "MethodArgument not valid exception"),
    QUEUE_EXCEPTION("QUEUE_EXCEPTION", "Queue exception");
    private String code;
    private String message;
}
