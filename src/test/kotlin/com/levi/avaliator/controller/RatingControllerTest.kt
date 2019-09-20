package com.levi.avaliator.controller

import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

import com.datastax.driver.core.utils.UUIDs
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.levi.avaliator.AvaliatorApplication
import com.levi.avaliator.dto.RatingDTO
import com.levi.avaliator.dto.UserDTO
import com.levi.avaliator.entity.Rating
import com.levi.avaliator.enumeration.ImprovementType
import com.levi.avaliator.enumeration.RangeTime
import com.levi.avaliator.service.RatingService
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import java.time.Instant
import java.util.*

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [AvaliatorApplication::class])
@AutoConfigureMockMvc
@ActiveProfiles("test")
class RatingControllerTest {

    @Autowired
    private val mvc: MockMvc? = null

    @MockBean
    private val service: RatingService? = null

    private var objectMapper: ObjectMapper? = null

    companion object {
        private const val URL_BASE = "/avaliator/rating/"
        private const val URL_FIND_BY_RESTAURANT = "/findByRestaurant/1"

        private val UUID : UUID = UUIDs.timeBased()
        private val INSTANT : Instant = Instant.now()

        private const val RATING_VALUE = 3.0
        private const val RATING_RESTAURANT_ID = 1
        private const val RATING_ORDER_ID = 1
        private const val FIRST_USER_ID = 1
        private const val FIRST_USER_NAME = "Test Name 1"
        private const val SECOND_USER_ID = 2
        private const val SECOND_USER_NAME = "Test Name 2"
        private const val RATING_COMMENT = "Test Comment"
    }


    @Before
    fun setUp() {
        objectMapper = ObjectMapper()
        objectMapper!!.registerModule(JavaTimeModule())
        objectMapper!!.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
    }

    @Test
    fun create() {
        BDDMockito.given(service!!.create(givenRating())).willReturn(givenRating())
        mvc!!.perform(MockMvcRequestBuilders.post(URL_BASE)
                .content(objectMapper!!.writeValueAsString(givenRating()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk)
                .andReturn().response.contentAsString
    }

    @Test
    fun findByRestaurant() {
        BDDMockito.given(service!!.retrieveRestaurantRatings(RATING_RESTAURANT_ID)).willReturn(givenRestaurantRatingsDTO())
        mvc!!.perform(MockMvcRequestBuilders.get(URL_BASE + URL_FIND_BY_RESTAURANT)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk)
                .andReturn().response.contentAsString
    }

    private fun givenRating() : Rating {
        return Rating(UUID, RATING_VALUE, RATING_RESTAURANT_ID, RATING_ORDER_ID, FIRST_USER_ID, RATING_COMMENT,
                INSTANT, true, RangeTime.UNTIL_FIFTEEN_MINUTES, ImprovementType.ATTENDIMENT)
    }

    private fun givenRestaurantRatingsDTO() : List<RatingDTO> {
        return listOf(RatingDTO(UUID, RATING_VALUE, RATING_RESTAURANT_ID, UserDTO(FIRST_USER_ID, FIRST_USER_NAME),
                RATING_COMMENT, INSTANT), RatingDTO(UUID, RATING_VALUE, RATING_RESTAURANT_ID,
                UserDTO(SECOND_USER_ID, SECOND_USER_NAME), RATING_COMMENT, INSTANT))
    }

}
