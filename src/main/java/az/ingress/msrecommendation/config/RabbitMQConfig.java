package az.ingress.msrecommendation.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;


@Configuration
public class RabbitMQConfig {
    private final String recommendationQ;
    private final String recommendationDLQ;
    private final String recommendationQExchange;
    private final String recommendationDLQExchange;
    private final String recommendationQKey;
    private final String recommendationDLQKey;

    public RabbitMQConfig(@Value("${rabbitmq.queue.recommendation}") String recommendationQ,
                          @Value("${rabbitmq.queue.recommendation-dlq}") String recommendationDLQ) {
        this.recommendationQ = recommendationQ;
        this.recommendationDLQ = recommendationDLQ;
        this.recommendationQExchange = recommendationQ + "_EXCHANGE";
        this.recommendationDLQExchange = recommendationDLQ + "_EXCHANGE";
        this.recommendationQKey = recommendationQ + "_KEY";
        this.recommendationDLQKey = recommendationDLQ + "_KEY";
    }

    @Bean
    public DirectExchange publisherDLQExchange() {
        return new DirectExchange(recommendationDLQExchange);
    }

    @Bean
    public DirectExchange publisherQExchange() {
        return new DirectExchange(recommendationQExchange);
    }

    @Bean
    public Queue publisherDLQ() {
        return QueueBuilder.durable(recommendationDLQ).build();
    }

    @Bean
    public Queue publisherQ() {
        return QueueBuilder.durable(recommendationQ)
                .withArgument("x-dead-letter-exchange", recommendationDLQExchange)
                .withArgument("x-dead-letter-routing-key", recommendationDLQKey)
                .build();
    }

    @Bean
    public Binding publisherDLQBinding() {
        return BindingBuilder.bind(publisherDLQ())
                .to(publisherDLQExchange()).with(recommendationDLQKey);
    }

    @Bean
    public Binding publisherQBinding() {
        return BindingBuilder.bind(publisherQ())
                .to(publisherQExchange()).with(recommendationQKey);
    }
}
