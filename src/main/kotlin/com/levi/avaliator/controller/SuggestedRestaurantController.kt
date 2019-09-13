package com.levi.avaliator.controller

import com.levi.avaliator.entity.SuggestedRestaurant
import com.levi.avaliator.service.SuggestedRestaurantService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/avaliator/suggestedRestaurant")
class SuggestedRestaurantController(private val service : SuggestedRestaurantService) {

    @PostMapping
    fun create(@RequestBody suggestedRestaurant: SuggestedRestaurant) : SuggestedRestaurant = service.create(suggestedRestaurant)

    @GetMapping("/getNonRegisteredSuggestedRestaurants")
    fun getNonRegisteredSuggestedRestaurants(@RequestParam radius : Double)  = service.retrieveNonRegisteredSuggestedRestaurants(radius)

}
