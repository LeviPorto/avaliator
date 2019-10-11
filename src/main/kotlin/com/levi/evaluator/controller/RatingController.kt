package com.levi.evaluator.controller

import com.levi.evaluator.domain.Rating
import com.levi.evaluator.service.RatingService
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/evaluator/rating")
class RatingController(private val service: RatingService) {

    @PostMapping
    fun create(@Valid @RequestBody rating: Rating): Rating = service.create(rating)

    @GetMapping("/restaurant/{restaurantId}")
    fun findByRestaurant(@PathVariable restaurantId : Int) = service.retrieveByRestaurant(restaurantId)
}
