package com.levi.evaluator.service

import com.levi.evaluator.dto.UnitAverageDTO
import com.levi.evaluator.service.cache.CachedService
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class CachedEvaluationProcessorService(private val redisTemplate: RedisTemplate<String, UnitAverageDTO>)
    : CachedService<String, UnitAverageDTO> {

    companion object {
        const val RATE_AVERAGE_CACHE_KEY = "RATE_AVERAGE_"
    }

    override fun retrieveInCache(key: String): UnitAverageDTO? {
        return redisTemplate.opsForValue().get(RATE_AVERAGE_CACHE_KEY + key)
    }

    override fun saveInCache(key: String, value: UnitAverageDTO, unit: Int, timeUnit: TimeUnit) {
        redisTemplate.opsForValue().set(RATE_AVERAGE_CACHE_KEY + key, value, 5, timeUnit)
    }

}
