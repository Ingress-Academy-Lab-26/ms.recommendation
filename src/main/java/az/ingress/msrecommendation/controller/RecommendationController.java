package az.ingress.msrecommendation.controller;

import az.ingress.msrecommendation.service.abstraction.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/recommendations")
public class RecommendationController {
    private final RecommendationService recommendationService;

    @GetMapping("/by-category")
    @PreAuthorize("@authService.verifyToken(#accessToken)")
    private List<Long> getRecommendationByCategory(@RequestParam Long categoryId, @RequestHeader(AUTHORIZATION) String accessToken) {
        return recommendationService.getRecommendationByCategory(categoryId);
    }

    @GetMapping("/by-product")
    @PreAuthorize("@authService.verifyToken(#accessToken)")
    private List<Long> getRecommendationByProduct(@RequestParam Long productId, @RequestHeader(AUTHORIZATION) String accessToken) {
        return recommendationService.getRecommendationByProduct(productId);
    }


}
