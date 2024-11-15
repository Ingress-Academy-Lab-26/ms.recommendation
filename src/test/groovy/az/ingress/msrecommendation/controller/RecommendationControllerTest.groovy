package az.ingress.msrecommendation.controller

import az.ingress.msrecommendation.exception.ErrorHandler
import az.ingress.msrecommendation.service.abstraction.RecommendationService
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static io.github.benas.randombeans.EnhancedRandomBuilder.aNewEnhancedRandom
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


class RecommendationControllerTest extends Specification {
    RecommendationService recommendationService
    RecommendationController recommendationController
    MockMvc mockMvc
    def random = aNewEnhancedRandom()

    def setup() {
        recommendationService = Mock()
        recommendationController = new RecommendationController(recommendationService)
        mockMvc = MockMvcBuilders.standaloneSetup(recommendationController)
                .setControllerAdvice(ErrorHandler.class)
                .build()
    }

    def "TestGetRecommendationByCategory"() {
        given:
        def categoryId = random.nextObject(Long)
        def accessToken = random.nextObject(String)
        def url = "/v1/recommendations/by-category"
        def recommendationResponse = [random.nextObject(Long)]

        when:
        def result = mockMvc.perform(get(url)
                .header("accessToken", accessToken)
                .param("categoryId", categoryId.toString())
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON))

        then:
        1 * recommendationService.getRecommendationByCategory(categoryId) >> recommendationResponse
        result.andExpectAll(
                status().isOk())
    }

    def "TestGetRecommendationByProduct"() {
        given:
        def productId = random.nextObject(Long)
        def accessToken = random.nextObject(String)
        def url = "v1/recommendations/by-product"
        def recommendationResponse = [random.nextObject(Long)]
        when:
        def result = mockMvc.perform(get(url)
                .header("accessToken", accessToken)
                .param("productId", productId.toString())
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON))


        then:
        1 * recommendationService.getRecommendationByProduct(productId) >> recommendationResponse
        result.andExpectAll(
                status().isOk())
    }


}
