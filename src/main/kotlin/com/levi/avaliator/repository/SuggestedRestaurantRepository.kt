package com.levi.avaliator.repository

import com.levi.avaliator.entity.SuggestedRestaurant
import org.springframework.data.cassandra.repository.CassandraRepository
import java.util.*

interface SuggestedRestaurantRepository : CassandraRepository<SuggestedRestaurant, UUID> {

    fun findByName(name : String) : SuggestedRestaurant?

}
