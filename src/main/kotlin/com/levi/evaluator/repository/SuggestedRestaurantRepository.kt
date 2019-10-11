package com.levi.evaluator.repository

import com.levi.evaluator.domain.SuggestedRestaurant
import org.springframework.data.cassandra.repository.CassandraRepository
import java.util.*

interface SuggestedRestaurantRepository : CassandraRepository<SuggestedRestaurant, UUID> {

    fun findByName(name: String): SuggestedRestaurant?

}
