package az.ingress.msrecommendation.queue

import az.ingress.msrecommendation.model.queue.RecommendationDto
import az.ingress.msrecommendation.queue.abstraction.RecommendationListener
import az.ingress.msrecommendation.queue.concrete.RecommendationListenerHandler
import az.ingress.msrecommendation.service.abstraction.RecommendationService
import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Specification

class RecommendationListenerTest extends Specification {
    RecommendationListener recommendationListener;
    RecommendationService recommendationService;
    ObjectMapper objectMapper

    def setup() {
        recommendationService = Mock()
        recommendationListener = new RecommendationListenerHandler(recommendationService)
    }

    def "TestConsume : success case"() {
        given:
        def message = """
                                   {
                                      "items": [
                                        {
                                          "productId": 1,
                                          "categoryId": 101,
                                          "quantity": 5
                                        },
                                        {
                                          "productId": 2,
                                          "categoryId": 102,
                                          "quantity": 10
                                        }
                                      ],
                                      "sourceType": "CART"
                                   }
                                """
        def data = objectMapper.readValue(message, RecommendationDto.class)

        when:
        recommendationListener.consume(message)
        then:
        1 * recommendationService.createRecommendation(data)
    }

}