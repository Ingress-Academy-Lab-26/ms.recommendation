package az.ingress.msrecommendation.model.cache;

import az.ingress.msrecommendation.model.enums.SourceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CacheData implements Serializable {
    private static final Long serialVersionUID = 1L;

    private Long productId;

    private Long secondUnitOfProductId;

    private Long quantity;

    private Long quantityPurchasedTogether;

    private SourceType sourceType;
}
