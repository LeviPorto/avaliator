package com.levi.evaluator.service

import com.levi.evaluator.api.ManagerApi
import com.levi.evaluator.domain.Rating
import com.levi.evaluator.dto.RatingDTO
import com.levi.evaluator.repository.RatingRepository
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.Caching
import org.springframework.stereotype.Service

@Service
class RatingService(private val repository: RatingRepository,
                    private val evaluationProcessorService: EvaluationProcessorService,
                    private val cachedEvaluationProcessorService: CachedEvaluationProcessorService,
                    private val managerApi: ManagerApi) {

    @Caching(evict = [CacheEvict(value = ["RATING_BY_RESTAURANT_ID_"], key = "{#rating.restaurantId}")])
    fun create(rating: Rating): Rating {
        val createdRating = repository.save(rating)

        verifyInCacheAndProcessRating(rating)

        return createdRating
    }

    fun retrieveRestaurantRatings(restaurantId: Int): List<RatingDTO> {
        val restaurantRatings = repository.findByRestaurantId(restaurantId)

        val evaluators = managerApi.retrieveByIds(restaurantRatings.map { it.userId })

        return restaurantRatings.map { restaurantRating ->
            val evaluator = evaluators.first { restaurantRating.userId == it.id }
            RatingDTO(restaurantRating.id, restaurantRating.value, restaurantRating.restaurantId, evaluator
                    , restaurantRating.comment, restaurantRating.date)
        }
    }

    @Cacheable(value = ["RATING_BY_RESTAURANT_ID_"], key = "{#restaurantId}", unless = "#result == null || #result.isEmpty()")
    fun retrieveByRestaurant(restaurantId: Int) : List<Rating> = repository.findByRestaurantId(restaurantId)

    private fun verifyInCacheAndProcessRating(rating: Rating) {
        val cachedRestaurantAverageRating = cachedEvaluationProcessorService.retrieveInCache(rating.restaurantId.toString())
        if (cachedRestaurantAverageRating == null) {
            evaluationProcessorService.processRating(cachedRestaurantAverageRating, rating, retrieveRestaurantRatings(rating.restaurantId))
        } else {
            evaluationProcessorService.processRating(cachedRestaurantAverageRating, rating)
        }
    }

}
