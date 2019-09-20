package com.levi.avaliator.repository

import com.datastax.driver.core.utils.UUIDs
import com.levi.avaliator.AvaliatorApplication
import com.levi.avaliator.entity.Rating
import com.levi.avaliator.enumeration.ImprovementType
import com.levi.avaliator.enumeration.RangeTime
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
class RatingRepositoryTest {

    @Autowired
    private val repository: RatingRepository? = null

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
    fun findByRestaurantId() {
        repository!!.save(givenRatingOfFirstRestaurant())
        repository.save(givenRatingOfFirstRestaurant())
        repository.save(givenRatingOfSecondRestaurant())
        val restaurantRatings = repository.findByRestaurantId(2)
        Assert.assertEquals(restaurantRatings.size, 1)
    }

    private fun givenRatingOfFirstRestaurant() : Rating {
        return Rating(uuid!!, 3.0, 1, 1, 1, "Test Comment!",
                instant!!, true, RangeTime.UNTIL_FIFTEEN_MINUTES, ImprovementType.ATTENDIMENT)
    }

    private fun givenRatingOfSecondRestaurant() : Rating {
        return Rating(uuid!!, 3.0, 2, 1, 1, "Test Comment!",
                instant!!, true, RangeTime.UNTIL_FIFTEEN_MINUTES, ImprovementType.ATTENDIMENT)
    }

}
