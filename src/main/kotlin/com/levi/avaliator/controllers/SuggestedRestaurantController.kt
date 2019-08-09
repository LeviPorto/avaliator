package com.levi.avaliator.controllers

import com.levi.avaliator.documents.SuggestedRestaurant
import com.levi.avaliator.services.SuggestedRestaurantService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/avaliator/suggestedRestaurant")
class SuggestedRestaurantController(private val service : SuggestedRestaurantService) {

    @PostMapping
    fun create(suggestedRestaurant: SuggestedRestaurant) : SuggestedRestaurant = service.create(suggestedRestaurant)

    @GetMapping("/getGoogleSuggestedRestaurants")
    fun getGoogleSuggestedRestaurants(@RequestParam radius : Double)  = service.retrieveNonRegisteredSuggestedRestaurants(radius)

}