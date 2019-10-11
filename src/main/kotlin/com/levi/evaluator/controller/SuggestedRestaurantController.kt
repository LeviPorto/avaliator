package com.levi.evaluator.controller

import com.levi.evaluator.domain.SuggestedRestaurant
import com.levi.evaluator.dto.SuggestedRestaurantDTO
import com.levi.evaluator.service.SuggestedRestaurantService
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/evaluator/suggestedRestaurant")
class SuggestedRestaurantController(private val service: SuggestedRestaurantService) {

    @PostMapping
    fun create(@Valid @RequestBody suggestedRestaurant: SuggestedRestaurant): SuggestedRestaurant = service.create(suggestedRestaurant)

    @GetMapping("/zomatoSearch")
    fun findSuggestedRestaurantsFromZomatoApi(@RequestHeader("user-key") userKey : String,
                                              @RequestParam("q") searchedWord : String,
                                              @RequestParam("entity_type") entityType : String) : List<SuggestedRestaurantDTO> =
            service.retrieveSuggestedRestaurantsFromZomatoApi(userKey, entityType, searchedWord)

}
