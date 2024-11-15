package az.ingress.msrecommendation.model.request;

import az.ingress.msrecommendation.model.enums.SourceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecommendationRequest {
    private Long productId;

    private Long relatedProductId;

    private Long categoryId;

    private Long quantity;

    private SourceType sourceType;
}


