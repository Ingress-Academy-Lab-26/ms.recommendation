package az.ingress.msrecommendation.model.queue;


import az.ingress.msrecommendation.model.enums.SourceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecommendationDto {
    private List<Items> items;
    private SourceType sourceType;
}
