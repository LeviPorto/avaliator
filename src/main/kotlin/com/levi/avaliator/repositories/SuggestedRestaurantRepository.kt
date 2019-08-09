package com.levi.avaliator.repositories

import com.levi.avaliator.documents.SuggestedRestaurant
import org.springframework.data.repository.CrudRepository
import java.util.*

interface SuggestedRestaurantRepository : CrudRepository<SuggestedRestaurant, UUID> {

    fun findByName(name : String) : SuggestedRestaurant

}