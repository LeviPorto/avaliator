package com.levi.evaluator.dto.zomato

import java.io.Serializable

data class ZomatoFullRestaurantDTO (
        val restaurant : ZomatoRestaurantDTO? = null
) : Serializable
