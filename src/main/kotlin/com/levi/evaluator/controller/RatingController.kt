package com.levi.evaluator.controller

import com.levi.evaluator.entity.Rating
import com.levi.evaluator.dto.RatingDTO
import com.levi.evaluator.service.RatingService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/evaluator/rating")
class RatingController(private val service: RatingService) {

    @PostMapping
    fun create(@RequestBody rating: Rating): Rating = service.create(rating)

    @GetMapping("/findByRestaurant/{restaurantId}")
    fun findByRestaurant(@PathVariable("restaurantId") restaurantId: Int): List<RatingDTO> = service.retrieveRestaurantRatings(restaurantId)

}
