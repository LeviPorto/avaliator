package com.levi.avaliator.repository

import com.datastax.driver.core.utils.UUIDs
import com.levi.avaliator.AvaliatorApplication
import com.levi.avaliator.entity.SuggestedRestaurant
import org.junit.After
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import java.util.*

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [AvaliatorApplication::class])
@ActiveProfiles("test")
class SuggestedRepositoryTest {

    @Autowired
    private val repository: SuggestedRestaurantRepository? = null

    companion object {
        private val UUID : UUID = UUIDs.timeBased()

        private const val SUGGESTED_RESTAURANT_NAME = "Test Restaurant"
        private const val SUGGESTED_RESTAURANT_ADDRESS = "Test Address"
        private const val SUGGESTED_RESTAURANT_COUNT = 3
        private const val SUGGESTED_RESTAURANT_PHONE = "Test Phone"
    }

    @After
    fun tearDown() {
        repository!!.deleteAll()
    }

    @Test
    fun findByName() {
        repository!!.save(givenSuggestedRestaurant())
        val restaurantRatings = repository.findByName(SUGGESTED_RESTAURANT_NAME)
        Assert.assertEquals(restaurantRatings!!.name, givenSuggestedRestaurant().name)
    }

    private fun givenSuggestedRestaurant() : SuggestedRestaurant {
        return SuggestedRestaurant(UUID, SUGGESTED_RESTAURANT_NAME, SUGGESTED_RESTAURANT_ADDRESS,
                SUGGESTED_RESTAURANT_COUNT, SUGGESTED_RESTAURANT_PHONE)
    }

}
