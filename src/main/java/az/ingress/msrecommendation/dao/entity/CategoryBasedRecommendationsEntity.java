package az.ingress.msrecommendation.dao.entity;


import az.ingress.msrecommendation.model.enums.SourceType;
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
import javax.persistence.Enumerated;
import javax.persistence.EnumType;

import static javax.persistence.GenerationType.IDENTITY;


@Entity
@Table(name = "category_based_recommendations")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryBasedRecommendationsEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private Long productId;

    private Long categoryId;

    private Long quantity;

    @Enumerated(EnumType.STRING)
    private SourceType sourceType;

}
