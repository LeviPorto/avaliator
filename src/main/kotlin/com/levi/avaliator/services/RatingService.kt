package com.levi.avaliator.services

import com.levi.avaliator.apis.ManagerApi
import com.levi.avaliator.publisher.RatingPublisher
import com.levi.avaliator.documents.Rating
import com.levi.avaliator.dtos.AvaliatedRestaurantDTO
import com.levi.avaliator.dtos.RatingDTO
import com.levi.avaliator.repositories.RatingRepository
import org.springframework.stereotype.Service

@Service
class RatingService(private val repository: RatingRepository,
                    private val avaliationProcessorService: AvaliationProcessorService,
                    private val ratingPublisher: RatingPublisher,
                    private val cachedAvaliationProcessorService: CachedAvaliationProcessorService,
                    private val managerApi : ManagerApi) {

    fun create(rating : Rating) : Rating {
        val createdRating = repository.insert(rating)

        val restaurantAverageRating = retrieveRestaurantAverageRating(rating)
        ratingPublisher.sendRatingToTopic(AvaliatedRestaurantDTO(rating.restaurantId, restaurantAverageRating, restaurantAverageRating > 4.5))

        return createdRating
    }

    fun retrieveRestaurantRatings(restaurantId : Int) : List<RatingDTO> {
        val restaurantRatings = repository.findByRestaurantId(restaurantId)

        val avaliators = managerApi.retrieveByIds(restaurantRatings.map {it.userId})

        return restaurantRatings.map { restaurantRating ->
            val avaliator = avaliators.first { restaurantRating.userId == it.id }
            RatingDTO(restaurantRating.id, restaurantRating.value, restaurantRating.restaurantId, avaliator
                    , restaurantRating.comment, restaurantRating.date)
        }
    }

    private fun retrieveRestaurantAverageRating(rating: Rating): Double {
        val cachedRestaurantAverageRating = cachedAvaliationProcessorService.retrieveInCache(rating.restaurantId.toString())

        return if (cachedRestaurantAverageRating == null) {
            avaliationProcessorService.calculateAverageRestaurantRatings(rating, retrieveRestaurantRatings(rating.restaurantId))
        } else {
            avaliationProcessorService.calculateCachedAverageRestaurantRatings(cachedRestaurantAverageRating, rating)
        }
    }

}
