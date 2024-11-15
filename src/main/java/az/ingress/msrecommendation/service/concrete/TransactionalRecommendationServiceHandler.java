package az.ingress.msrecommendation.service.concrete;

import az.ingress.msrecommendation.aspect.Loggable;
import az.ingress.msrecommendation.dao.repository.CategoryBasedRecommendationsRepository;
import az.ingress.msrecommendation.dao.repository.ProductBasedRecommendationsRepository;
import az.ingress.msrecommendation.model.request.RecommendationRequest;
import az.ingress.msrecommendation.service.abstraction.CacheService;
import az.ingress.msrecommendation.service.abstraction.TransactionalRecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static az.ingress.msrecommendation.mapper.RecommendationMapper.RECOMMENDATION_MAPPER;
import static az.ingress.msrecommendation.model.constants.RetryConstants.MAX_ATTEMPTS;
import static az.ingress.msrecommendation.model.constants.RetryConstants.DELAY_TIME;
import static org.springframework.transaction.annotation.Isolation.REPEATABLE_READ;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Service
@RequiredArgsConstructor
@Loggable
public class TransactionalRecommendationServiceHandler implements TransactionalRecommendationService {
    private final CategoryBasedRecommendationsRepository categoryBasedRecommendationsRepository;
    private final ProductBasedRecommendationsRepository productBasedRecommendationsRepository;
    private final CacheService cacheService;

    @Override
    @Transactional(rollbackFor = Exception.class, isolation = REPEATABLE_READ, propagation = REQUIRES_NEW)
    @Retryable(value = Exception.class, maxAttempts = MAX_ATTEMPTS, backoff = @Backoff(delay = DELAY_TIME))
    public void saveToOrderBasedRecommendations(RecommendationRequest recommendationRequest) {
        var existingRecommendation = productBasedRecommendationsRepository.findByFirstUnitOfProductIdAndSecondUnitOfProductId(recommendationRequest.getProductId(), recommendationRequest.getRelatedProductId());
        if (existingRecommendation.isPresent()) {
            var recommendation = existingRecommendation.get();
            recommendation.incrementquantityPurchasedTogether();
            productBasedRecommendationsRepository.save(recommendation);
        } else {
            productBasedRecommendationsRepository.save(RECOMMENDATION_MAPPER.createProductBasedRecommendationsEntity(recommendationRequest));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, isolation = REPEATABLE_READ, propagation = REQUIRES_NEW)
    @Retryable(value = Exception.class, maxAttempts = MAX_ATTEMPTS, backoff = @Backoff(delay = DELAY_TIME))
    public void saveToCategoryBasedRecommendations(RecommendationRequest recommendationRequest) {
        var existingRecommendation = categoryBasedRecommendationsRepository.findByProductId(recommendationRequest.getProductId());
        if (existingRecommendation.isPresent()) {
            var recommendations = existingRecommendation.get();
            recommendations.setQuantity(recommendationRequest.getQuantity());
            categoryBasedRecommendationsRepository.save(recommendations);
        } else {
            categoryBasedRecommendationsRepository.save(RECOMMENDATION_MAPPER.createCategoryBasedRecommendationsEntity(recommendationRequest));
        }
    }

    @Override
    @Async
    public void saveCacheToOrderBasedRecommendations(Long productId) {
        var orderBasedRecommendations = productBasedRecommendationsRepository.findByFirstUnitOfProductId(productId);
        var productKey = "product-id:" + productId;
        var cacheData = orderBasedRecommendations.stream()
                .map(RECOMMENDATION_MAPPER::createCacheData)
                .toList();
        cacheService.saveCache(cacheData, productKey);
    }

    @Override
    @Async
    public void saveCacheToCategoryBasedRecommendations(Long categoryId) {
        var categoryBasedRecommendations = categoryBasedRecommendationsRepository.findByCategoryId(categoryId);
        var categoryKey = "category-id:" + categoryId;
        var cacheData = categoryBasedRecommendations.stream()
                .map(RECOMMENDATION_MAPPER::createCacheData)
                .toList();
        cacheService.saveCache(cacheData, categoryKey);
    }
}
