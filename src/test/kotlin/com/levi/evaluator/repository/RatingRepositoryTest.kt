package com.levi.evaluator.repository

import com.datastax.driver.core.utils.UUIDs
import com.levi.evaluator.EvaluatorApplication
import com.levi.evaluator.entity.Rating
import com.levi.evaluator.enumeration.ImprovementType
import com.levi.evaluator.enumeration.RangeTime
import org.junit.After
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import java.time.Instant
import java.util.*

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [EvaluatorApplication::class])
@ActiveProfiles("test")
class RatingRepositoryTest {

    @Autowired
    private val repository: RatingRepository? = null

    companion object {
        private val UUID: UUID = UUIDs.timeBased()
        private val INSTANT: Instant = Instant.now()

        private const val RATING_VALUE = 3.0
        private const val RATING_FIRST_RESTAURANT_ID = 1
        private const val RATING_SECOND_RESTAURANT_ID = 2
        private const val RATING_ORDER_ID = 1
        private const val FIRST_USER_ID = 1
        private const val RATING_COMMENT = "Test Comment"
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
        val restaurantRatings = repository.findByRestaurantId(RATING_SECOND_RESTAURANT_ID)
        Assert.assertEquals(restaurantRatings.size, 1)
    }

    private fun givenRatingOfFirstRestaurant(): Rating {
        return Rating(UUID, RATING_VALUE, RATING_FIRST_RESTAURANT_ID, RATING_ORDER_ID, FIRST_USER_ID, RATING_COMMENT,
                INSTANT, true, RangeTime.UNTIL_FIFTEEN_MINUTES, ImprovementType.ATTENDIMENT)
    }

    private fun givenRatingOfSecondRestaurant(): Rating {
        return Rating(UUID, RATING_VALUE, RATING_SECOND_RESTAURANT_ID, RATING_ORDER_ID, FIRST_USER_ID, RATING_COMMENT,
                INSTANT, true, RangeTime.UNTIL_FIFTEEN_MINUTES, ImprovementType.ATTENDIMENT)
    }

}
