package com.levi.avaliator.repository

import com.datastax.driver.core.utils.UUIDs
import com.levi.avaliator.AvaliatorApplication
import com.levi.avaliator.entity.SuggestedRestaurant
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import java.time.Instant
import java.util.*

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [AvaliatorApplication::class])
@ActiveProfiles("test")
class SuggestedRepositoryTest {

    @Autowired
    private val repository: SuggestedRestaurantRepository? = null

    private var uuid : UUID? = null
    private var instant : Instant? = null

    @Before
    fun setUp() {
        uuid = UUIDs.timeBased()
        instant = Instant.now()
    }

    @After
    fun tearDown() {
        repository!!.deleteAll()
    }

    @Test
    fun findByName() {
        repository!!.save(givenSuggestedRestaurant())
        val restaurantRatings = repository.findByName("Test Restaurant")
        Assert.assertEquals(restaurantRatings!!.name, givenSuggestedRestaurant().name)
    }

    private fun givenSuggestedRestaurant() : SuggestedRestaurant {
        return SuggestedRestaurant(UUIDs.timeBased(), "Test Restaurant", "Test Address", 3, "Test Phone")
    }

}
