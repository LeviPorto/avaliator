package com.levi.avaliator.services

import com.levi.avaliator.apis.ManagerApi
import com.levi.avaliator.publisher.RatePublisher
import com.levi.avaliator.documents.Rate
import com.levi.avaliator.dtos.AvaliatedRestaurantDTO
import com.levi.avaliator.dtos.RateDTO
import com.levi.avaliator.repositories.RateRepository
import org.springframework.stereotype.Service

@Service
class RateService(private val repository: RateRepository,
                  private val avaliatorProcessorService: AvaliatorProcessorService,
                  private val ratePublisher: RatePublisher,
                  private val cachedAvaliatorProcessorService: CachedAvaliatorProcessorService,
                  private val managerApi : ManagerApi) {

    fun create(rate : Rate) : Rate {
        val createdRate = repository.insert(rate)

        val restaurantsAverageRate = retrieveRestaurantsAverageRate(rate)
        ratePublisher.sendRateToTopic(AvaliatedRestaurantDTO(rate.restaurantId, restaurantsAverageRate, restaurantsAverageRate > 4.5))

        return createdRate
    }

    fun retrieveRestaurantRates(restaurantId : Int) : List<RateDTO> {
        val restaurantRates = repository.findByRestaurantId(restaurantId)

       //TODO Fazer em batch o retrieve de ID (ver se é possível)

        return restaurantRates.map {
            RateDTO(it.id, it.value, it.restaurantId, managerApi.retrieveById(it.userId), it.comment, it.date)
        }
    }

    private fun retrieveRestaurantsAverageRate(rate: Rate): Double {
        val cachedRestaurantAverageRate = cachedAvaliatorProcessorService.retrieveInCache(rate.restaurantId.toString())

        return if (cachedRestaurantAverageRate == null) {
            avaliatorProcessorService.calculateAverageRestaurantRate(rate, retrieveRestaurantRates(rate.restaurantId))
        } else {
            avaliatorProcessorService.calculateCachedAverageRestaurantRate(cachedRestaurantAverageRate, rate)
        }
    }

}
