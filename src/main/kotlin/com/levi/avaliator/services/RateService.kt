package com.levi.avaliator.services

import com.levi.avaliator.apis.ManagerApi
import com.levi.avaliator.dispatcher.RateDispatcher
import com.levi.avaliator.documents.Rate
import com.levi.avaliator.dtos.AvaliatedRestaurantDTO
import com.levi.avaliator.dtos.RateDTO
import com.levi.avaliator.dtos.UserDTO
import com.levi.avaliator.repositories.RateRepository
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class RateService(private val repository: RateRepository,
                  private val avaliatorProcessorService: AvaliatorProcessorService,
                  private val cachedAvaliatorProcessorService: CachedAvaliatorProcessorService,
                  private val rateDispatcher: RateDispatcher,
                  private val managerApi : ManagerApi) {

    fun create(rate : Rate) : Rate {
        val createdRate = repository.insert(rate)

        val restaurantsAverageRate = retrieveRestaurantsAverageRate(rate)

        rateDispatcher.sendRateToTopic(AvaliatedRestaurantDTO(rate.restaurantId,
                restaurantsAverageRate, rate.value > 4.5))
        return createdRate
    }

    fun retrieveRestaurantRates(restaurantId : Int) : List<RateDTO> {
        val restaurantRates = repository.findByRestaurantId(restaurantId)

        val avaliators = managerApi.retrieveByIds(
                restaurantRates.stream().map { it.userId }.collect(Collectors.toList())
        ).stream().map { UserDTO(it.id, it.name) }.collect(Collectors.toList())

        return restaurantRates.map {
            RateDTO(it.id, it.value, it.restaurantId, avaliators[restaurantRates.indexOf(it)], it.comment, it.date)
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