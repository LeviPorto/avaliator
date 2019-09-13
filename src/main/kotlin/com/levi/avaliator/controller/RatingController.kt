package com.levi.avaliator.controller

import com.levi.avaliator.entity.Rating
import com.levi.avaliator.dto.RatingDTO
import com.levi.avaliator.service.RatingService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/avaliator/rating")
class RatingController(private val service : RatingService) {

    @PostMapping
    fun create(@RequestBody rating : Rating) : Rating = service.create(rating)

    @GetMapping("/findBy/{restaurantId}")
    fun findByRestaurant(@PathVariable("restaurantId") restaurantId : Int) : List<RatingDTO> = service.retrieveRestaurantRatings(restaurantId)

}
