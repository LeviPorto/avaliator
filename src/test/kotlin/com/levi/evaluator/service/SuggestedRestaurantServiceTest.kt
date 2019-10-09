package com.levi.evaluator.service

import com.datastax.driver.core.utils.UUIDs
import com.levi.evaluator.EvaluatorApplication
import com.levi.evaluator.entity.SuggestedRestaurant
import com.levi.evaluator.repository.SuggestedRestaurantRepository
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import java.util.*

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [EvaluatorApplication::class])
@ActiveProfiles("test")
class SuggestedRestaurantServiceTest {

    @Autowired
    private val service: SuggestedRestaurantService? = null

    @MockBean
    private val repository: SuggestedRestaurantRepository? = null

    companion object {
        private val UUID: UUID = UUIDs.timeBased()

        private const val SUGGESTED_RESTAURANT_NAME = "Test Restaurant"
        private const val SUGGESTED_RESTAURANT_ADDRESS = "Test Address"
        private const val SUGGESTED_RESTAURANT_COUNT = 3
        private const val SUGGESTED_RESTAURANT_PHONE = "Test Phone"
    }

    @Test
    fun createWithExistent() {
        BDDMockito.given(repository!!.save(Mockito.any(SuggestedRestaurant::class.java))).willReturn(givenSuggestedRestaurant())
        BDDMockito.given(repository.findByName(givenSuggestedRestaurant().name)).willReturn(givenSuggestedRestaurant())
        val createdSuggestedRestaurant = service!!.create(givenSuggestedRestaurant())
        Assert.assertNotNull(createdSuggestedRestaurant)
        Assert.assertEquals(createdSuggestedRestaurant.count, 3)
    }

    @Test
    fun createWithoutExistent() {
        BDDMockito.given(repository!!.save(Mockito.any(SuggestedRestaurant::class.java))).willReturn(givenFirstSuggestedRestaurant())
        BDDMockito.given(repository.findByName(givenFirstSuggestedRestaurant().name)).willReturn(null)
        val createdSuggestedRestaurant = service!!.create(givenFirstSuggestedRestaurant())
        Assert.assertNotNull(createdSuggestedRestaurant)
        Assert.assertEquals(createdSuggestedRestaurant.count, 1)
    }

    private fun givenFirstSuggestedRestaurant(): SuggestedRestaurant {
        return SuggestedRestaurant(UUID, SUGGESTED_RESTAURANT_NAME, SUGGESTED_RESTAURANT_ADDRESS, SUGGESTED_RESTAURANT_PHONE)
    }

    private fun givenSuggestedRestaurant(): SuggestedRestaurant {
        return SuggestedRestaurant(UUID, SUGGESTED_RESTAURANT_NAME, SUGGESTED_RESTAURANT_ADDRESS, SUGGESTED_RESTAURANT_COUNT,
                SUGGESTED_RESTAURANT_PHONE)
    }

}
