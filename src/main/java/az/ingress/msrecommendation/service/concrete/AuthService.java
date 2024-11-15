package az.ingress.msrecommendation.service.concrete;

import az.ingress.msrecommendation.client.AuthClient;
import az.ingress.msrecommendation.model.client.AuthPayloadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service(value = "authService")
@RequiredArgsConstructor
public class AuthService {
    private final AuthClient authClient;

    public AuthPayloadDto verifyToken(String accessToken) {
        return authClient.verifyToken(accessToken);

    }
}
