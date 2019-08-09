package com.levi.avaliator.services

import com.levi.avaliator.documents.SuggestedRestaurant
import com.levi.avaliator.repositories.SuggestedRestaurantRepository
import org.springframework.stereotype.Service

@Service
class SuggestedRestaurantService(private val repository: SuggestedRestaurantRepository) {

    fun create(suggestedRestaurant: SuggestedRestaurant) : SuggestedRestaurant {
        val existentSuggestedRestaurant = repository.findByName(suggestedRestaurant.name)

        return if(existentSuggestedRestaurant == null) {
            repository.save(suggestedRestaurant)
        } else {
            repository.save(SuggestedRestaurant(suggestedRestaurant.id, suggestedRestaurant.name,
                    suggestedRestaurant.address, suggestedRestaurant.count, suggestedRestaurant.phone, 1))
        }

    }

    fun retrieveNonRegisteredSuggestedRestaurants(radius : Double) {
        /**
         * This method get all restaurants registered in google maps, parsed to my entity, filtered by non-existent
         * in my database. I have to pay to get google API :( . It can be possible to pay.
         */
    }

}