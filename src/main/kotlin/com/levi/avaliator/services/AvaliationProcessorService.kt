package com.levi.avaliator.services

import com.levi.avaliator.documents.Rating
import com.levi.avaliator.dtos.UnitAverageDTO
import com.levi.avaliator.dtos.RatingDTO
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class AvaliationProcessorService(private val cachedAvaliationProcessorService: CachedAvaliationProcessorService) {

    fun calculateAverageRestaurantRatings(rating: Rating, restaurantRatings : List<RatingDTO>): Double {

        val restaurantRatingsCount = restaurantRatings.stream().count().toInt()
        val restaurantRatingsSum = restaurantRatings.stream().mapToDouble { it.value }.sum()

        cachedAvaliationProcessorService.saveInCache(rating.restaurantId.toString(), UnitAverageDTO(restaurantRatingsSum,
                restaurantRatingsCount), 5, TimeUnit.HOURS)

        return restaurantRatingsSum / restaurantRatingsCount
    }

    fun calculateCachedAverageRestaurantRatings(cachedAverageRestaurantRating : UnitAverageDTO, rating: Rating): Double {
        val calculatedCountCachedAverageRestaurantRating = cachedAverageRestaurantRating.count + 1
        val calculatedSumCachedAverageRestaurantRating = cachedAverageRestaurantRating.sum + rating.value

        cachedAvaliationProcessorService.saveInCache(rating.restaurantId.toString(), UnitAverageDTO(
                calculatedSumCachedAverageRestaurantRating, calculatedCountCachedAverageRestaurantRating), 5, TimeUnit.HOURS)

        return calculatedSumCachedAverageRestaurantRating / calculatedCountCachedAverageRestaurantRating
    }

}