package com.levi.evaluator.dto.zomato

import java.io.Serializable

data class ZomatoRestaurantDTO (
        val name : String? = null,
        val location : ZomatoLocationDTO? = null,
        val phone_numbers : String? = null
) : Serializable
