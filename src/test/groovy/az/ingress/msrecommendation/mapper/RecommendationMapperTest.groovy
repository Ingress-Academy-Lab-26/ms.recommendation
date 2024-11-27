package az.ingress.msrecommendation.mapper

import az.ingress.msrecommendation.dao.entity.CategoryBasedRecommendationsEntity
import az.ingress.msrecommendation.model.cache.CacheData
import az.ingress.msrecommendation.model.request.RecommendationRequest
import spock.lang.Specification

import static az.ingress.msrecommendation.mapper.RecommendationMapper.RECOMMENDATION_MAPPER
import static io.github.benas.randombeans.EnhancedRandomBuilder.aNewEnhancedRandom

class RecommendationMapperTest extends Specification {
    def random = aNewEnhancedRandom();

    def "TestCreateCategoryBasedRecommendationsEntity: with RecommendationRequest"() {
        given:
        def recommendationRequest = random.nextObject(RecommendationRequest)

        when:
        def actualResult = RECOMMENDATION_MAPPER.createCategoryBasedRecommendationsEntity(recommendationRequest)

        then:
        actualResult.productId == recommendationRequest.productId
        actualResult.categoryId == recommendationRequest.categoryId
        actualResult.quantity == recommendationRequest.quantity
        actualResult.sourceType == recommendationRequest.sourceType
    }

    def "TestCreateCategoryBasedRecommendationsEntity: with CacheData"() {
        given:
        def cacheData = random.nextObject(CacheData)

        when:
        def actualResult = RECOMMENDATION_MAPPER.createCategoryBasedRecommendationsEntity(cacheData)

        then:
        actualResult.productId == cacheData.productId
        actualResult.quantity == cacheData.quantity
        actualResult.sourceType == cacheData.sourceType
    }

    def "TestCreateProductBasedRecommendationsEntity"() {
        given:
        def recommendationRequest = random.nextObject(RecommendationRequest)

        when:
        def actualResult = RECOMMENDATION_MAPPER.createProductBasedRecommendationsEntity(recommendationRequest)

        then:
        actualResult.firstUnitOfProductId == recommendationRequest.productId
        actualResult.secondUnitOfProductId == recommendationRequest.relatedProductId
    }

    def "TestCreateCacheData: with CategoryBasedRecommendationsEntity"() {
        given:
        def categoryBasedRecommendations = random.nextObject(CategoryBasedRecommendationsEntity)

        when:
        def actualResult = RECOMMENDATION_MAPPER.createCacheData(categoryBasedRecommendations)

        then:
        actualResult.productId == categoryBasedRecommendations.productId
        actualResult.quantity == categoryBasedRecommendations.quantity
        actualResult.sourceType == categoryBasedRecommendations.sourceType
    }

    def "TestCreateCacheData: with ProductBasedRecommendationsEntity"() {
        given:
        def productBasedRecommendationsEntity = random.nextObject(ProductBasedRecommendationsEntity)

        when:
        def actualResult = RECOMMENDATION_MAPPER.createCacheData(productBasedRecommendationsEntity)

        then:
        actualResult.secondUnitOfProductId == productBasedRecommendationsEntity.secondUnitOfProductId
        actualResult.quantityPurchasedTogether == productBasedRecommendationsEntity.quantityPurchasedTogether
    }
}