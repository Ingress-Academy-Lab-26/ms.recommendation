package az.ingress.msrecommendation.dao.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.Builder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "product_based_recommendations")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductBasedRecommendationsEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private Long firstUnitOfProductId;

    private Long secondUnitOfProductId;

    private Integer quantityPurchasedTogether;

    public void incrementquantityPurchasedTogether() {
        this.quantityPurchasedTogether++;
    }
}
