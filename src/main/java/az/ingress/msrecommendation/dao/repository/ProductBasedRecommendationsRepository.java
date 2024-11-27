package az.ingress.msrecommendation.dao.repository;

import az.ingress.msrecommendation.dao.entity.ProductBasedRecommendationsEntity;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductBasedRecommendationsRepository extends CrudRepository<ProductBasedRecommendationsEntity, Long> {

    @Query("SELECT p FROM ProductBasedRecommendationsEntity p WHERE p.firstUnitOfProductId = :firstUnitOfProductId ORDER BY p.quantityPurchasedTogether DESC")
    List<ProductBasedRecommendationsEntity> findByFirstUnitOfProductId(@Param("firstUnitOfProductId") Long firstUnitOfProductId);

    Optional<ProductBasedRecommendationsEntity> findByFirstUnitOfProductIdAndSecondUnitOfProductId(Long firstUnitOfProductId, Long secondUnitOfProductId);
}
