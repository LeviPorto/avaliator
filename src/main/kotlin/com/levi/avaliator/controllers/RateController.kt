package com.levi.avaliator.controllers

import com.levi.avaliator.documents.Rate
import com.levi.avaliator.dtos.RateDTO
import com.levi.avaliator.services.RateService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/avaliator/rate")
class RateController(private val service : RateService) {

    @PostMapping
    fun create(@RequestBody rate : Rate) : Rate = service.create(rate)

    @GetMapping("/findBy/{restaurantId}")
    fun findByRestaurant(@PathVariable("restaurantId") restaurantId : Int) : List<RateDTO> = service.retrieveRestaurantRates(restaurantId)

}
