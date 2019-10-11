package com.levi.evaluator.service

import com.levi.evaluator.domain.SuggestedRestaurant
import com.levi.evaluator.dto.SuggestedRestaurantDTO
import com.levi.evaluator.dto.zomato.ZomatoSearchResponseDTO
import com.levi.evaluator.repository.SuggestedRestaurantRepository
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import kotlin.streams.toList

@Service
class SuggestedRestaurantService(private val repository: SuggestedRestaurantRepository) {

    fun create(suggestedRestaurant: SuggestedRestaurant): SuggestedRestaurant {
        val existentSuggestedRestaurant = repository.findByName(suggestedRestaurant.name)

        return if (existentSuggestedRestaurant == null) {
            repository.save(SuggestedRestaurant(suggestedRestaurant.id, suggestedRestaurant.name,
                    suggestedRestaurant.address, suggestedRestaurant.count + 1, suggestedRestaurant.phone))
        } else {
            repository.save(SuggestedRestaurant(existentSuggestedRestaurant.id, existentSuggestedRestaurant.name,
                    existentSuggestedRestaurant.address, existentSuggestedRestaurant.count + 1, existentSuggestedRestaurant.phone))
        }

    }

    fun retrieveSuggestedRestaurantsFromZomatoApi(userKey : String, entityType : String, searchedWord : String) : List<SuggestedRestaurantDTO> {
        val restTemplate = RestTemplate()

        val (builder, entity) = buildSearchZomatoRestaurantsTemplate(userKey, entityType, searchedWord)

        val response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, ZomatoSearchResponseDTO::class.java)

        return response.body!!.restaurants!!.stream().map { SuggestedRestaurantDTO(it.restaurant!!.name!!, it.restaurant.location!!.address!!, it.restaurant.phone_numbers!!) }.toList()
    }

    private fun buildSearchZomatoRestaurantsTemplate(userKey: String, entityType: String, searchedWord: String): Pair<UriComponentsBuilder, HttpEntity<ZomatoSearchResponseDTO>> {
        val headers = HttpHeaders()
        headers.set("user-key", userKey)

        val builder = UriComponentsBuilder.fromHttpUrl("https://developers.zomato.com/api/v2.1/search")
                .queryParam("entity_type", entityType)
                .queryParam("q", searchedWord)

        val entity = HttpEntity<ZomatoSearchResponseDTO>(headers)

        return Pair(builder, entity)
    }

}
