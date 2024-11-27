package az.ingress.msrecommendation.mapper;

import az.ingress.msrecommendation.dao.entity.ProductBasedRecommendationsEntity;
import az.ingress.msrecommendation.dao.entity.CategoryBasedRecommendationsEntity;
import az.ingress.msrecommendation.model.cache.CacheData;
import az.ingress.msrecommendation.model.request.RecommendationRequest;

public enum RecommendationMapper {
    RECOMMENDATION_MAPPER;

    public CategoryBasedRecommendationsEntity createCategoryBasedRecommendationsEntity(RecommendationRequest recommendationRequest) {
        return CategoryBasedRecommendationsEntity.builder()
                .productId(recommendationRequest.getProductId())
                .categoryId(recommendationRequest.getCategoryId())
                .quantity(recommendationRequest.getQuantity())
                .sourceType(recommendationRequest.getSourceType())
                .build();
    }

    public CategoryBasedRecommendationsEntity createCategoryBasedRecommendationsEntity(CacheData cacheData) {
        return CategoryBasedRecommendationsEntity.builder()
                .productId(cacheData.getProductId())
                .quantity(cacheData.getQuantity())
                .sourceType(cacheData.getSourceType())
                .build();
    }

    public ProductBasedRecommendationsEntity createProductBasedRecommendationsEntity(RecommendationRequest recommendationRequest) {
        return ProductBasedRecommendationsEntity.builder()
                .firstUnitOfProductId(recommendationRequest.getProductId())
                .secondUnitOfProductId(recommendationRequest.getRelatedProductId())
                .quantityPurchasedTogether(1)
                .build();
    }

    public CacheData createCacheData(CategoryBasedRecommendationsEntity categoryBasedRecommendationsEntity) {
        return CacheData.builder()
                .productId(categoryBasedRecommendationsEntity.getProductId())
                .quantity(categoryBasedRecommendationsEntity.getQuantity())
                .sourceType(categoryBasedRecommendationsEntity.getSourceType())
                .build();
    }

    public CacheData createCacheData(ProductBasedRecommendationsEntity productBasedRecommendationsEntity) {
        return CacheData.builder()
                .secondUnitOfProductId(productBasedRecommendationsEntity.getSecondUnitOfProductId())
                .quantityPurchasedTogether(productBasedRecommendationsEntity.getFirstUnitOfProductId())
                .build();
    }

}
