package com.levi.avaliator.services

import com.levi.avaliator.documents.Rate
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

//TODO veirificar todos os DTOs
//TODO Melhorar nomes das variáveis aqui
//TODO Camada cache

@Service
class AvaliatorProcessorService(private val rateService : RateService,
                                private val redisTemplate: RedisTemplate<String, List<Double>>) {

    companion object {
        const val RATE_AVERAGE_CACHE_KEY = "RATE_AVERAGE_"
        const val SUM_INDEX = 0
        const val COUNT_INDEX = 1
    }

    fun retrieveCalculatedRestaurantRate(rate : Rate) : Double {
        val cachedAverageRestaurantRate = redisTemplate.opsForValue().get(RATE_AVERAGE_CACHE_KEY + rate.restaurantId)

        return if(cachedAverageRestaurantRate == null) {
            calculateAverageRestaurantRate(rate)
        } else {
            calculateCachedAverageRestaurantRate(cachedAverageRestaurantRate, rate)
        }
    }

    private fun calculateAverageRestaurantRate(rate: Rate): Double {
        val restaurantsRate = rateService.retrieveRestaurantRates(rate.restaurantId)

        val restaurantsRateCount = restaurantsRate.stream().count().toDouble()
        val restaurantsRateSum = restaurantsRate.stream().mapToDouble { it.value }.sum()

        redisTemplate.opsForValue().set(RATE_AVERAGE_CACHE_KEY + rate.restaurantId,
                listOf(restaurantsRateSum, restaurantsRateCount), 5, TimeUnit.HOURS)

        return restaurantsRateSum / restaurantsRateCount
    }

    private fun calculateCachedAverageRestaurantRate(cachedAverageRestaurantRate : List<Double>, rate: Rate): Double {
        val calculatedSumCachedAverageRestaurantRate = cachedAverageRestaurantRate[SUM_INDEX] + rate.value
        val calculatedCountCachedAverageRestaurantRate = cachedAverageRestaurantRate[COUNT_INDEX] + 1

        redisTemplate.opsForValue().set(RATE_AVERAGE_CACHE_KEY + rate.restaurantId,
                listOf(calculatedSumCachedAverageRestaurantRate, calculatedCountCachedAverageRestaurantRate), 5, TimeUnit.HOURS)

        return calculatedSumCachedAverageRestaurantRate / calculatedCountCachedAverageRestaurantRate
    }

}