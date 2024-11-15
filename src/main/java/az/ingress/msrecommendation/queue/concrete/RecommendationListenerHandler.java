package az.ingress.msrecommendation.queue.concrete;

import az.ingress.msrecommendation.exception.QueueException;
import az.ingress.msrecommendation.model.queue.RecommendationDto;
import az.ingress.msrecommendation.queue.abstraction.RecommendationListener;
import az.ingress.msrecommendation.service.abstraction.RecommendationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static az.ingress.msrecommendation.model.enums.ExceptionConstants.QUEUE_EXCEPTION;

@Component
@RequiredArgsConstructor
@Slf4j
public class RecommendationListenerHandler implements RecommendationListener {
    private final ObjectMapper objectMapper;
    private final RecommendationService recommendationService;

    @Override
    @RabbitListener(queues = "${rabbitmq.queue.recommendation}")
    public void consume(String message) {
        try {
            var data = objectMapper.readValue(message, RecommendationDto.class);
            recommendationService.createRecommendation(data);
        } catch (JsonProcessingException e) {
            log.error("ActionLog.consume.error message invalid format: {}", message);
        } catch (Exception e) {
            throw new QueueException(QUEUE_EXCEPTION.getCode(), QUEUE_EXCEPTION.getMessage());
        }

    }
}
