package com.levi.evaluator.dto.zomato

import java.io.Serializable

data class ZomatoSearchResponseDTO (
        val restaurants : List<ZomatoFullRestaurantDTO>? = null
) : Serializable
