package az.ingress.msrecommendation.client.decoder;

import az.ingress.msrecommendation.exception.ClientException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;

import static az.ingress.msrecommendation.client.decoder.JsonNodeFieldName.CODE;
import static az.ingress.msrecommendation.client.decoder.JsonNodeFieldName.MESSAGE;
import static az.ingress.msrecommendation.model.enums.ExceptionConstants.CLIENT_ERROR;

public class CustomerErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        var errorMessage = CLIENT_ERROR.getMessage();
        var errorCode = CLIENT_ERROR.getCode();
        JsonNode jsonNode;

        try (var body = response.body().asInputStream()) {
            jsonNode = new ObjectMapper().readValue(body, JsonNode.class);
        } catch (Exception e) {
            throw new ClientException(errorMessage, errorCode, response.status());
        }
        if (jsonNode.has(MESSAGE.getValue())) {
            errorMessage = jsonNode.get(MESSAGE.getValue()).asText();
        }
        if (jsonNode.has(CODE.getValue())) {
            errorCode = jsonNode.get(CODE.getValue()).asText();
        }
        return new ClientException(errorMessage, errorCode, response.status());
    }
}
