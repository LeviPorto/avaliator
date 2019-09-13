package com.levi.avaliator.controllers

import com.levi.avaliator.documents.Rating
import com.levi.avaliator.dtos.RatingDTO
import com.levi.avaliator.services.RatingService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/avaliator/rating")
class RatingController(private val service : RatingService) {

    @PostMapping
    fun create(@RequestBody rating : Rating) : Rating = service.create(rating)

    @GetMapping("/findBy/{restaurantId}")
    fun findByRestaurant(@PathVariable("restaurantId") restaurantId : Int) : List<RatingDTO> = service.retrieveRestaurantRatings(restaurantId)

}
