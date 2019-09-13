package com.levi.avaliator.dto

import java.time.Instant
import java.util.*

class RatingDTO  (
        val id : UUID,
        val value : Double,
        val restaurantId : Int,
        val userDTO : UserDTO,
        val comment : String,
        val date : Instant
)
