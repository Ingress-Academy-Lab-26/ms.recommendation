package az.ingress.msrecommendation.service.abstraction;

import az.ingress.msrecommendation.model.queue.RecommendationDto;

import java.util.List;

public interface RecommendationService {
    void createRecommendation(RecommendationDto recommendationDto);

    List<Long> getRecommendationByProduct(Long productId);

    List<Long> getRecommendationByCategory(Long categoryId);
}
