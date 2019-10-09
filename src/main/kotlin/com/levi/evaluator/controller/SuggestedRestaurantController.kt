package com.levi.evaluator.controller

import com.levi.evaluator.entity.SuggestedRestaurant
import com.levi.evaluator.service.SuggestedRestaurantService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/evaluator/suggestedRestaurant")
class SuggestedRestaurantController(private val service: SuggestedRestaurantService) {

    @PostMapping
    fun create(@RequestBody suggestedRestaurant: SuggestedRestaurant): SuggestedRestaurant = service.create(suggestedRestaurant)

    @GetMapping("/getNonRegisteredSuggestedRestaurants")
    fun getNonRegisteredSuggestedRestaurants(@RequestParam radius: Double) = service.retrieveNonRegisteredSuggestedRestaurants(radius)

}
