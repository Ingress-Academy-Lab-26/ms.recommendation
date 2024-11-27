package az.ingress.msrecommendation.client;

import az.ingress.msrecommendation.client.decoder.CustomerErrorDecoder;
import az.ingress.msrecommendation.model.client.AuthPayloadDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@FeignClient(name = "ms-auth", url = "${spring.client.urls.ms-auth}", configuration = CustomerErrorDecoder.class)
public interface AuthClient {
    @PostMapping("internal/v1/token/verify")
    AuthPayloadDto verifyToken(@RequestHeader(AUTHORIZATION) String accessToken);
}
