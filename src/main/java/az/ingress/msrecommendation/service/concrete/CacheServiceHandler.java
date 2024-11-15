package az.ingress.msrecommendation.service.concrete;

import az.ingress.msrecommendation.aspect.Loggable;
import az.ingress.msrecommendation.model.cache.CacheData;
import az.ingress.msrecommendation.service.abstraction.CacheService;
import az.ingress.msrecommendation.util.CacheUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static az.ingress.msrecommendation.model.constants.CacheConstants.CACHE_EXPIRE_SECONDS;
import static az.ingress.msrecommendation.model.constants.CacheConstants.CACHE_PREFIX;
import static java.time.temporal.ChronoUnit.DAYS;

@Service
@RequiredArgsConstructor
@Loggable
public class CacheServiceHandler implements CacheService {
    private final CacheUtil cacheUtil;

    @Override
    public void saveCache(List<CacheData> cacheData, String key) {
        var cacheKey = CACHE_PREFIX + key;
        cacheUtil.saveToCache(cacheKey, cacheData, CACHE_EXPIRE_SECONDS, DAYS);
    }

    @Override
    public List<CacheData> getBucket(String key) {
        var cacheKey = CACHE_PREFIX + key;
        return cacheUtil.getBucket(cacheKey);
    }
}
