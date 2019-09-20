package com.levi.avaliator.service

import com.levi.avaliator.dto.AvaliatedRestaurantDTO
import com.levi.avaliator.entity.Rating
import com.levi.avaliator.dto.UnitAverageDTO
import com.levi.avaliator.dto.RatingDTO
import com.levi.avaliator.publisher.RatingPublisher
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class AvaliationProcessorService(private val cachedService: CachedAvaliationProcessorService,
                                 private val ratingPublisher: RatingPublisher) {

    @Async
    fun processRating(cachedRestaurantAverageRating: UnitAverageDTO?, rating: Rating, previousRatings: List<RatingDTO>? = null) {
        val calculatedRating = if (cachedRestaurantAverageRating == null) {
            calculateAverageRestaurantRatings(rating, previousRatings!!)
        } else {
            calculateCachedAverageRestaurantRatings(rating, cachedRestaurantAverageRating)
        }

        ratingPublisher.sendRatingToTopic(AvaliatedRestaurantDTO(rating.restaurantId, calculatedRating, calculatedRating > 4.5))
    }

    fun calculateAverageRestaurantRatings(rating: Rating, restaurantRatings : List<RatingDTO>): Double {

        val restaurantRatingsCount = restaurantRatings.stream().count().toInt()
        val restaurantRatingsSum = restaurantRatings.stream().mapToDouble { it.value }.sum()

        cachedService.saveInCache(rating.restaurantId.toString(), UnitAverageDTO(restaurantRatingsSum,
                restaurantRatingsCount), 5, TimeUnit.HOURS)

        return restaurantRatingsSum / restaurantRatingsCount
    }

    fun calculateCachedAverageRestaurantRatings(rating: Rating, cachedAverageRestaurantRating : UnitAverageDTO): Double {
        val calculatedCountCachedAverageRestaurantRating = cachedAverageRestaurantRating.count + 1
        val calculatedSumCachedAverageRestaurantRating = cachedAverageRestaurantRating.sum + rating.value

        cachedService.saveInCache(rating.restaurantId.toString(), UnitAverageDTO(
                calculatedSumCachedAverageRestaurantRating, calculatedCountCachedAverageRestaurantRating), 5, TimeUnit.HOURS)

        return calculatedSumCachedAverageRestaurantRating / calculatedCountCachedAverageRestaurantRating
    }

}
