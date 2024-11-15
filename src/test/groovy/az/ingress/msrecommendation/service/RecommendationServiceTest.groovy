package az.ingress.msrecommendation.service

import az.ingress.msrecommendation.dao.entity.CategoryBasedRecommendationsEntity
import az.ingress.msrecommendation.dao.repository.CategoryBasedRecommendationsRepository
import az.ingress.msrecommendation.dao.repository.ProductBasedRecommendationsRepository
import az.ingress.msrecommendation.service.concrete.CacheServiceHandler
import az.ingress.msrecommendation.service.concrete.RecommendationServiceHandler
import az.ingress.msrecommendation.service.concrete.TransactionalRecommendationServiceHandler
import static io.github.benas.randombeans.EnhancedRandomBuilder.aNewEnhancedRandom
import io.github.benas.randombeans.api.EnhancedRandom
import spock.lang.Specification

class RecommendationServiceTest extends Specification {
    EnhancedRandom random = aNewEnhancedRandom()
    ProductBasedRecommendationsRepository productBasedRecommendationsRepository
    CategoryBasedRecommendationsRepository categoryBasedRecommendationsRepository
    CacheServiceHandler cacheServiceHandler
    TransactionalRecommendationServiceHandler transactionalRecommendationServiceHandler
    RecommendationServiceHandler recommendationServiceHandler

    def setup() {
        productBasedRecommendationsRepository = Mock()
        categoryBasedRecommendationsRepository = Mock()
        categoryBasedRecommendationsRepository = Mock()
        cacheServiceHandler = Mock()
        transactionalRecommendationServiceHandler = Mock()
        recommendationServiceHandler = new RecommendationServiceHandler(categoryBasedRecommendationsRepository, productBasedRecommendationsRepository, cacheServiceHandler, transactionalRecommendationServiceHandler);
    }

    def "TestGetRecommendationByProduct success case"() {
        given:
        def productId = random.nextObject(Long)
        def recommendation = random.nextObject(CategoryBasedRecommendationsEntity)
        when:
        def response =recommendationServiceHandler.getRecommendationByProduct(productId)
        then:
        1 * productBasedRecommendationsRepository.findByFirstUnitOfProductId(productId) >> List.of(recommendation)
    }
}
