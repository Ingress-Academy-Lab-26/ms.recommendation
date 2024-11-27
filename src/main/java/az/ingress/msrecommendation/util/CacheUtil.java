package az.ingress.msrecommendation.util;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;


import java.time.Duration;
import java.time.temporal.TemporalUnit;

import static az.ingress.msrecommendation.model.constants.CacheConstants.CACHE_PREFIX;


@Component
@RequiredArgsConstructor
public class CacheUtil {
    private final RedissonClient redissonClient;

    public <T> T getBucket(String cacheKey) {
        RBucket<T> bucket = getRBucket(cacheKey);
        return bucket == null ? null : bucket.get();
    }

    public <T> void saveToCache(String cacheKey, T value, Long expireTime, TemporalUnit temporalUnit) {
        var bucket = getRBucket(cacheKey);
        bucket.set(value);
        bucket.expire(Duration.of(expireTime, temporalUnit));
    }

    private <T> RBucket<T> getRBucket(String cacheKey) {
        var key = CACHE_PREFIX.formatted(cacheKey);
        return redissonClient.getBucket(key);
    }
}
