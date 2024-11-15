package az.ingress.msrecommendation.dao.repository;

import az.ingress.msrecommendation.dao.entity.CategoryBasedRecommendationsEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


public interface CategoryBasedRecommendationsRepository extends CrudRepository<CategoryBasedRecommendationsEntity, Long> {

    List<CategoryBasedRecommendationsEntity> findByCategoryId(Long categoryId);

    Optional<CategoryBasedRecommendationsEntity> findByProductId(Long productId);

}
