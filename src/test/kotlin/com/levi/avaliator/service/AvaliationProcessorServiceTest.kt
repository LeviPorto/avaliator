package com.levi.avaliator.service

import com.datastax.driver.core.utils.UUIDs
import com.levi.avaliator.AvaliatorApplication
import com.levi.avaliator.dto.RatingDTO
import com.levi.avaliator.dto.UnitAverageDTO
import com.levi.avaliator.dto.UserDTO
import com.levi.avaliator.entity.Rating
import com.levi.avaliator.enumeration.ImprovementType
import com.levi.avaliator.enumeration.RangeTime
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import java.time.Instant
import java.util.*

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [AvaliatorApplication::class])
@ActiveProfiles("test")
class AvaliationProcessorServiceTest {

    @Autowired
    private val avaliationProcessorService: AvaliationProcessorService? = null

    @MockBean
    private val cachedAvaliationProcessorService: CachedAvaliationProcessorService? = null



    companion object {
        private val UUID : UUID = UUIDs.timeBased()
        private val INSTANT : Instant = Instant.now()

        private const val DELTA = 1e-15

        private const val RATING_VALUE_ONE = 1.0
        private const val RATING_VALUE_THREE = 3.0
        private const val RATING_RESTAURANT_ID = 1
        private const val RATING_ORDER_ID = 1
        private const val FIRST_USER_ID = 1
        private const val FIRST_USER_NAME = "Test Name 1"
        private const val SECOND_USER_ID = 2
        private const val SECOND_USER_NAME = "Test Name 2"
        private const val RATING_COMMENT = "Test Comment"
        private const val UNIT_AVERAGE_RATING_COUNT = 1
        private const val UNIT_AVERAGE_RATING_SUM = 4.0
    }

    @Test
    fun calculateAverageRestaurantRatings() {
        val ratings = givenRatings()
        val calculatedValue = avaliationProcessorService!!.calculateAverageRestaurantRatings(givenActualRating(), ratings)
        Assert.assertEquals(2.0, calculatedValue, DELTA)
    }

    @Test
    fun calculateCachedAverageRestaurantRatings() {
        val cachedUnitAverage = givenUnitAverageStoredInCache()
        val calculatedValue = avaliationProcessorService!!.calculateCachedAverageRestaurantRatings(givenActualRating(), cachedUnitAverage)
        Assert.assertEquals(3.5, calculatedValue, DELTA)
    }

    private fun givenActualRating() : Rating {
        return Rating(UUID, RATING_VALUE_THREE, RATING_RESTAURANT_ID, RATING_ORDER_ID, FIRST_USER_ID, RATING_COMMENT,
                INSTANT, true, RangeTime.UNTIL_FIFTEEN_MINUTES, ImprovementType.ATTENDIMENT)
    }

    private fun givenRatings() : List<RatingDTO> {
        return listOf(RatingDTO(UUID, RATING_VALUE_THREE, RATING_RESTAURANT_ID, UserDTO(FIRST_USER_ID, FIRST_USER_NAME),
                RATING_COMMENT, INSTANT), RatingDTO(UUID, RATING_VALUE_ONE, RATING_RESTAURANT_ID,
                UserDTO(SECOND_USER_ID, SECOND_USER_NAME), RATING_COMMENT, INSTANT))
    }

    private fun givenUnitAverageStoredInCache() : UnitAverageDTO {
        return UnitAverageDTO(UNIT_AVERAGE_RATING_SUM, UNIT_AVERAGE_RATING_COUNT)
    }

}
