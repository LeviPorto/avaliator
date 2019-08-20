package com.levi.avaliator.services

import com.levi.avaliator.documents.Rate
import com.levi.avaliator.dtos.AverageUnitDTO
import com.levi.avaliator.dtos.RateDTO
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class AvaliatorProcessorService(private val cachedAvaliatorProcessorService: CachedAvaliatorProcessorService) {

    fun calculateAverageRestaurantRate(rate: Rate, restaurantsRate : List<RateDTO>): Double {

        val restaurantsRateCount = restaurantsRate.stream().count().toInt()
        val restaurantsRateSum = restaurantsRate.stream().mapToDouble { it.value }.sum()

        cachedAvaliatorProcessorService.saveInCache(rate.restaurantId.toString(), AverageUnitDTO(restaurantsRateSum,
                restaurantsRateCount), 5, TimeUnit.HOURS)

        return restaurantsRateSum / restaurantsRateCount
    }

    fun calculateCachedAverageRestaurantRate(cachedAverageRestaurantRate : AverageUnitDTO, rate: Rate): Double {
        val calculatedSumCachedAverageRestaurantRate = cachedAverageRestaurantRate.sum + rate.value
        val calculatedCountCachedAverageRestaurantRate = cachedAverageRestaurantRate.count + 1

        cachedAvaliatorProcessorService.saveInCache(rate.restaurantId.toString(), AverageUnitDTO(
                calculatedSumCachedAverageRestaurantRate, calculatedCountCachedAverageRestaurantRate), 5, TimeUnit.HOURS)

        return calculatedSumCachedAverageRestaurantRate / calculatedCountCachedAverageRestaurantRate
    }

}