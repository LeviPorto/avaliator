package com.levi.avaliator.controllers

import com.levi.avaliator.documents.Rate
import com.levi.avaliator.dtos.RateDTO
import com.levi.avaliator.services.RateService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/avaliator/rate")
class RateController(private val service : RateService) {

    @PostMapping
    fun create(rate : Rate) : Rate = service.create(rate)

    @GetMapping("/findByRestaurant")
    fun findByRestaurant(restaurantId : Int) : List<RateDTO> = service.retrieveRestaurantRates(restaurantId)

}