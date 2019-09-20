package com.levi.avaliator.controller

import com.datastax.driver.core.utils.UUIDs
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.levi.avaliator.AvaliatorApplication
import com.levi.avaliator.entity.SuggestedRestaurant
import com.levi.avaliator.service.SuggestedRestaurantService
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [AvaliatorApplication::class])
@AutoConfigureMockMvc
@ActiveProfiles("test")
class SuggestedRestaurantControllerTest {

    @Autowired
    private val mvc: MockMvc? = null

    @MockBean
    private val service: SuggestedRestaurantService? = null

    private val URL_BASE = "/avaliator/suggestedRestaurant/"
    private var objectMapper: ObjectMapper? = null

    @Before
    fun setUp() {
        objectMapper = ObjectMapper()
        val module = JavaTimeModule()
        objectMapper!!.registerModule(module)
        objectMapper!!.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @Test
    fun create() {
        BDDMockito.given(service!!.create(givenSuggestedRestaurant())).willReturn(givenSuggestedRestaurant())
        mvc!!.perform(MockMvcRequestBuilders.post(URL_BASE)
                .content(objectMapper!!.writeValueAsString(givenSuggestedRestaurant()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andReturn().response.contentAsString
    }

    private fun givenSuggestedRestaurant() : SuggestedRestaurant {
        return SuggestedRestaurant(UUIDs.timeBased(), "Test Restaurant", "Test Address", 3, "Test Phone")
    }

}