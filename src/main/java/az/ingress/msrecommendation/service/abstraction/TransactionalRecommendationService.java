package az.ingress.msrecommendation.service.abstraction;

import az.ingress.msrecommendation.model.request.RecommendationRequest;

public interface TransactionalRecommendationService {
    void saveToOrderBasedRecommendations(RecommendationRequest recommendationRequest);

    void saveToCategoryBasedRecommendations(RecommendationRequest recommendationRequest);

    void saveCacheToOrderBasedRecommendations(Long productId);

    void saveCacheToCategoryBasedRecommendations(Long categoryId);
}
