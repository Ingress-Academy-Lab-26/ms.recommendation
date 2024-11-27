package az.ingress.msrecommendation.service.concrete;

import az.ingress.msrecommendation.aspect.Loggable;
import az.ingress.msrecommendation.dao.entity.ProductBasedRecommendationsEntity;
import az.ingress.msrecommendation.dao.entity.CategoryBasedRecommendationsEntity;
import az.ingress.msrecommendation.dao.repository.ProductBasedRecommendationsRepository;
import az.ingress.msrecommendation.dao.repository.CategoryBasedRecommendationsRepository;
import az.ingress.msrecommendation.model.cache.CacheData;
import az.ingress.msrecommendation.model.queue.Items;
import az.ingress.msrecommendation.model.queue.RecommendationDto;
import az.ingress.msrecommendation.model.request.RecommendationRequest;
import az.ingress.msrecommendation.service.abstraction.CacheService;
import az.ingress.msrecommendation.service.abstraction.RecommendationService;
import az.ingress.msrecommendation.service.abstraction.TransactionalRecommendationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;


import static az.ingress.msrecommendation.mapper.RecommendationMapper.RECOMMENDATION_MAPPER;
import static az.ingress.msrecommendation.model.enums.SourceType.*;


@Service
@RequiredArgsConstructor
@Loggable
@Slf4j
public class RecommendationServiceHandler implements RecommendationService {
    private final CategoryBasedRecommendationsRepository categoryBasedRecommendationsRepository;
    private final ProductBasedRecommendationsRepository productBasedRecommendationsRepository;
    private final CacheService cacheService;
    private final TransactionalRecommendationService transactionalRecommendationService;

    @Override
    public void createRecommendation(RecommendationDto recommendationDto) {
        var productIds = recommendationDto.getItems().stream()
                .map(Items::getProductId)
                .toList();
        var quantities = recommendationDto.getItems().stream()
                .map(Items::getQuantity)
                .toList();
        var categoryIds = recommendationDto.getItems().stream()
                .map(Items::getCategoryId)
                .toList();
        for (int i = 0; i < productIds.size(); i++) {
            for (int j = i + 1; j < productIds.size(); j++) {
                var productId = productIds.get(i);
                var categoryId = categoryIds.get(i);
                var productQuantity = quantities.get(i);
                var relatedProductId = productIds.get(j);
                if (ORDER.equals(recommendationDto.getSourceType())) {
                    var orderBasedRecommendations = RecommendationRequest.builder()
                            .productId(productId)
                            .relatedProductId(relatedProductId)
                            .build();
                    transactionalRecommendationService.saveToOrderBasedRecommendations(orderBasedRecommendations);
                    transactionalRecommendationService.saveCacheToOrderBasedRecommendations(productId);
                }
                var categoryBasedRecommendations = RecommendationRequest.builder()
                        .productId(productId)
                        .categoryId(categoryId)
                        .quantity(productQuantity)
                        .sourceType(recommendationDto.getSourceType())
                        .build();
                transactionalRecommendationService.saveToCategoryBasedRecommendations(categoryBasedRecommendations);
                transactionalRecommendationService.saveCacheToCategoryBasedRecommendations(categoryId);
            }
        }
    }

    @Override
    public List<Long> getRecommendationByProduct(Long productId) {
        var productKey = "product-id:" + productId;
        try {
            var recommendations = cacheService.getBucket(productKey);
            if (recommendations != null) {
                return recommendations.stream()
                        .sorted(Comparator.comparing(CacheData::getQuantityPurchasedTogether))
                        .limit(3)
                        .map(CacheData::getSecondUnitOfProductId)
                        .toList();
            }
        } catch (Exception e) {
            log.error("ActionLog.getRecommendationByProduct.error when read from cache:{}", e.getMessage(), e);
        }
        var products = productBasedRecommendationsRepository.findByFirstUnitOfProductId(productId);
        var cacheData = products.stream()
                .map(RECOMMENDATION_MAPPER::createCacheData)
                .toList();
        cacheService.saveCache(cacheData, productKey);
        return products.stream()
                .limit(3)
                .map(ProductBasedRecommendationsEntity::getSecondUnitOfProductId)
                .toList();

    }

    @Override
    public List<Long> getRecommendationByCategory(Long categoryId) {
        var categoryKey = "category-id:" + categoryId;
        try {
            var recommendations = cacheService.getBucket(categoryKey);
            if (recommendations != null) {
                return createCategoryBasedRecommendations(recommendations.stream()
                        .map(RECOMMENDATION_MAPPER::createCategoryBasedRecommendationsEntity)
                        .toList());
            }
        } catch (Exception e) {
            log.error("ActionLog.getRecommendationByCategory.error when read from cache:{}", e.getMessage(), e);
        }
        var allProducts = categoryBasedRecommendationsRepository.findByCategoryId(categoryId);
        var cacheData = allProducts.stream()
                .map(RECOMMENDATION_MAPPER::createCacheData)
                .toList();
        cacheService.saveCache(cacheData, categoryKey);
        return createCategoryBasedRecommendations(allProducts);
    }

    private List<Long> createCategoryBasedRecommendations(List<CategoryBasedRecommendationsEntity> allProducts) {

        var addedProducts = new HashSet<Long>();

        var cartProducts = allProducts.stream()
                .filter(product -> CART.equals(product.getSourceType()))
                .sorted(Comparator.comparing(CategoryBasedRecommendationsEntity::getQuantity).reversed())
                .filter(product -> addedProducts.add(product.getId()))
                .limit(4)
                .map(CategoryBasedRecommendationsEntity::getProductId)
                .toList();

        var wishlistProducts = allProducts.stream()
                .filter(product -> WISHLIST.equals(product.getSourceType()))
                .sorted(Comparator.comparing(CategoryBasedRecommendationsEntity::getQuantity).reversed())
                .limit(3)
                .filter(product -> addedProducts.add(product.getId()))
                .map(CategoryBasedRecommendationsEntity::getProductId)
                .toList();

        var orderProducts = allProducts.stream()
                .filter(product -> ORDER.equals(product.getSourceType()))
                .sorted(Comparator.comparing(CategoryBasedRecommendationsEntity::getQuantity).reversed())
                .limit(3)
                .filter(product -> addedProducts.add(product.getId()))
                .map(CategoryBasedRecommendationsEntity::getProductId)
                .toList();
        var result = new ArrayList<Long>();
        result.addAll(cartProducts);
        result.addAll(wishlistProducts);
        result.addAll(orderProducts);
        return result;
    }

}
