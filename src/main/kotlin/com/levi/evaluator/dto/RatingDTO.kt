package com.levi.evaluator.dto

import java.time.Instant
import java.util.*

class RatingDTO(
        val id: UUID,
        val value: Double,
        val restaurantId: Int,
        val userDTO: UserDTO,
        val comment: String,
        val date: Instant
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RatingDTO

        if (value != other.value) return false
        if (restaurantId != other.restaurantId) return false
        if (userDTO != other.userDTO) return false
        if (comment != other.comment) return false
        if (date != other.date) return false

        return true
    }

}
