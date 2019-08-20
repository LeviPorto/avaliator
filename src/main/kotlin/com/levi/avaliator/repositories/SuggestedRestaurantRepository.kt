package com.levi.avaliator.repositories

import com.levi.avaliator.documents.SuggestedRestaurant
import org.springframework.data.cassandra.repository.CassandraRepository
import java.util.*

interface SuggestedRestaurantRepository : CassandraRepository<SuggestedRestaurant, UUID> {

    fun findByName(name : String) : SuggestedRestaurant

}