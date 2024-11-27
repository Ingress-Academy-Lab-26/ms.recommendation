package az.ingress.msrecommendation.model.queue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Items {
    private Long productId;
    private Long categoryId;
    private Long quantity;
}
