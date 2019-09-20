package com.levi.avaliator.controller

import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
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
import org.mockito.Mockito
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

    private var uuid : UUID? = null
    private var instant : Instant? = null

    private val URL_BASE = "/avaliator/rating/"
    private var objectMapper: ObjectMapper? = null

    @Before
    fun setUp() {
        uuid = UUIDs.timeBased()
        instant = Instant.now()
        objectMapper = ObjectMapper()
        val module = JavaTimeModule()
        objectMapper!!.registerModule(module)
        objectMapper!!.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
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
        BDDMockito.given(service!!.retrieveRestaurantRatings(1)).willReturn(givenRestaurantRatingsDTO())
        mvc!!.perform(MockMvcRequestBuilders.get("$URL_BASE/findBy/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk)
                .andReturn().response.contentAsString
    }

    private fun givenRating() : Rating {
        return Rating(uuid!!, 3.0, 1, 1, 1, "Test Comment!",
                instant!!, true, RangeTime.UNTIL_FIFTEEN_MINUTES, ImprovementType.ATTENDIMENT)
    }

    private fun givenRestaurantRatingsDTO() : List<RatingDTO> {
        return listOf(RatingDTO(uuid!!, 3.0, 1, UserDTO(1, "Test Name 1"),
                "Test Comment!", instant!!), RatingDTO(uuid!!, 1.0, 1,
                UserDTO(2, "Test Name 2"), "Test Comment!", instant!!))
    }

}
