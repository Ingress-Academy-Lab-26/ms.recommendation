package az.ingress.msrecommendation.model.response;

import az.ingress.msrecommendation.model.enums.SourceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecommendationResponse {
    private Long id;
    private Long productId;
    private Long categoryId;
    private Long quantity;
    private SourceType sourceType;
}
