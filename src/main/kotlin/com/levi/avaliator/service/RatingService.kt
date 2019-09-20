package com.levi.avaliator.service

import com.levi.avaliator.api.ManagerApi
import com.levi.avaliator.entity.Rating
import com.levi.avaliator.dto.RatingDTO
import com.levi.avaliator.repository.RatingRepository
import org.springframework.stereotype.Service

@Service
class RatingService(private val repository: RatingRepository,
                    private val avaliationProcessorService: AvaliationProcessorService,
                    private val cachedAvaliationProcessorService: CachedAvaliationProcessorService,
                    private val managerApi : ManagerApi) {

    fun create(rating : Rating) : Rating {
        val createdRating = repository.save(rating)

        verifyInCacheAndProcessRating(rating)

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

    private fun verifyInCacheAndProcessRating(rating: Rating) {
        val cachedRestaurantAverageRating = cachedAvaliationProcessorService.retrieveInCache(rating.restaurantId.toString())
        if (cachedRestaurantAverageRating == null) {
            avaliationProcessorService.processRating(cachedRestaurantAverageRating, rating, retrieveRestaurantRatings(rating.restaurantId))
        } else {
            avaliationProcessorService.processRating(cachedRestaurantAverageRating, rating)
        }
    }

}
