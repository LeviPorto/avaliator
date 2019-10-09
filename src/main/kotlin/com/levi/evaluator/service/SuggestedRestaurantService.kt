package com.levi.evaluator.service

import com.levi.evaluator.entity.SuggestedRestaurant
import com.levi.evaluator.repository.SuggestedRestaurantRepository
import org.springframework.stereotype.Service

@Service
class SuggestedRestaurantService(private val repository: SuggestedRestaurantRepository) {

    fun create(suggestedRestaurant: SuggestedRestaurant): SuggestedRestaurant {
        val existentSuggestedRestaurant = repository.findByName(suggestedRestaurant.name)

        return if (existentSuggestedRestaurant == null) {
            repository.save(SuggestedRestaurant(suggestedRestaurant.id, suggestedRestaurant.name,
                    suggestedRestaurant.address, suggestedRestaurant.count + 1, suggestedRestaurant.phone))
        } else {
            repository.save(SuggestedRestaurant(existentSuggestedRestaurant.id, existentSuggestedRestaurant.name,
                    existentSuggestedRestaurant.address, existentSuggestedRestaurant.count + 1, existentSuggestedRestaurant.phone))
        }

    }

    fun retrieveNonRegisteredSuggestedRestaurants(radius: Double) {
        /**
         * This method get all restaurants registered in google maps, parsed to my entity, filtered by non-existent
         * in my database. I have to pay to get google API :( . It can´t be possible to pay.
         */
    }

}
