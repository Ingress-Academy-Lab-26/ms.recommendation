package az.ingress.msrecommendation.queue.abstraction;

public interface RecommendationListener {
    void consume(String message);
}
