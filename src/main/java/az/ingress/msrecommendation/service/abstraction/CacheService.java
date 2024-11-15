package az.ingress.msrecommendation.service.abstraction;

import az.ingress.msrecommendation.model.cache.CacheData;

import java.util.List;

public interface CacheService {
    void saveCache(List<CacheData> cacheData, String key);

    List<CacheData> getBucket(String key);
}
