package com.levi.avaliator.services

import com.levi.avaliator.dtos.AverageUnitDTO
import com.levi.avaliator.services.cache.CachedService
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class CachedAvaliatorProcessorService(private val redisTemplate: RedisTemplate<String, AverageUnitDTO>)
    : CachedService<String, AverageUnitDTO> {

    companion object {
        const val RATE_AVERAGE_CACHE_KEY = "RATE_AVERAGE_"
    }

    override fun retrieveInCache(key: String): AverageUnitDTO? {
        return redisTemplate.opsForValue().get(RATE_AVERAGE_CACHE_KEY + key)
    }

    override fun saveInCache(key: String, value: AverageUnitDTO, unit: Int, timeUnit: TimeUnit) {
        redisTemplate.opsForValue().set(RATE_AVERAGE_CACHE_KEY + key, value, 5, timeUnit)
    }

}