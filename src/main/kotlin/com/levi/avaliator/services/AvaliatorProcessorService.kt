package com.levi.avaliator.services

import com.levi.avaliator.documents.Rate
import com.levi.avaliator.dtos.AverageUnitDTO
import com.levi.avaliator.services.CachedAvaliatorProcessorService.Companion.RATE_AVERAGE_CACHE_KEY
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class AvaliatorProcessorService(private val rateService : RateService,
                                private val cachedAvaliatorProcessorService: CachedAvaliatorProcessorService) {

    fun retrieveCalculatedRestaurantRate(rate : Rate) : Double {
        val cachedAverageRestaurantRate = cachedAvaliatorProcessorService.retrieveInCache(rate.restaurantId.toString())

        return if(cachedAverageRestaurantRate == null) {
            calculateAverageRestaurantRate(rate)
        } else {
            calculateCachedAverageRestaurantRate(cachedAverageRestaurantRate, rate)
        }
    }

    private fun calculateAverageRestaurantRate(rate: Rate): Double {
        val restaurantsRate = rateService.retrieveRestaurantRates(rate.restaurantId)

        val restaurantsRateCount = restaurantsRate.stream().count().toInt()
        val restaurantsRateSum = restaurantsRate.stream().mapToDouble { it.value }.sum()

        cachedAvaliatorProcessorService.saveInCache(rate.restaurantId.toString(), AverageUnitDTO(restaurantsRateSum,
                restaurantsRateCount), 5, TimeUnit.HOURS)

        return restaurantsRateSum / restaurantsRateCount
    }

    private fun calculateCachedAverageRestaurantRate(cachedAverageRestaurantRate : AverageUnitDTO, rate: Rate): Double {
        val calculatedSumCachedAverageRestaurantRate = cachedAverageRestaurantRate.sum + rate.value
        val calculatedCountCachedAverageRestaurantRate = cachedAverageRestaurantRate.count + 1

        cachedAvaliatorProcessorService.saveInCache(rate.restaurantId.toString(), AverageUnitDTO(
                calculatedSumCachedAverageRestaurantRate, calculatedCountCachedAverageRestaurantRate), 5, TimeUnit.HOURS)

        return calculatedSumCachedAverageRestaurantRate / calculatedCountCachedAverageRestaurantRate
    }

}