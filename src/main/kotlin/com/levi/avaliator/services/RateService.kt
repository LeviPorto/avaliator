package com.levi.avaliator.services

import com.levi.avaliator.dispatcher.RateDispatcher
import com.levi.avaliator.documents.Rate
import com.levi.avaliator.dtos.AvaliatedRestaurantDTO
import com.levi.avaliator.repositories.RateRepository
import org.springframework.stereotype.Service

@Service
class RateService(private val repository: RateRepository,
                  private val avaliatorProcessorService: AvaliatorProcessorService,
                  private val rateDispatcher: RateDispatcher) {

    fun create(rate : Rate) : Rate {
        val createdRate = repository.insert(rate)
        rateDispatcher.sendRateToTopic(AvaliatedRestaurantDTO(rate.restaurantId,
                avaliatorProcessorService.retrieveCalculatedRestaurantRate(rate), rate.value > 4.5))
        return createdRate
    }

    fun retrieveRestaurantRates(restaurantId : Int) : List<Rate> = repository.findByRestaurantId(restaurantId)

}