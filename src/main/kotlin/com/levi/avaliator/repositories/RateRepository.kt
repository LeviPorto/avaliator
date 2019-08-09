package com.levi.avaliator.repositories

import com.levi.avaliator.documents.Rate
import org.springframework.data.cassandra.repository.CassandraRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RateRepository : CassandraRepository<Rate, UUID> {

    fun findByRestaurantId(restaurantId : Int) : List<Rate>

}