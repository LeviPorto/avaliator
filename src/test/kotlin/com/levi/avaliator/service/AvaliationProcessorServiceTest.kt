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
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import java.time.Instant

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [AvaliatorApplication::class])
@ActiveProfiles("test")
class AvaliationProcessorServiceTest {

    @Autowired
    private val avaliationProcessorService: AvaliationProcessorService? = null

    @MockBean
    private val cachedAvaliationProcessorService: CachedAvaliationProcessorService? = null

    private var actualRating : Rating? = null
    private val delta = 1e-15

    @Before
    fun setUp() {
        actualRating = givenActualRating()
    }

    @Test
    fun calculateAverageRestaurantRatings() {
        val ratings = givenRatings()
        val calculatedValue = avaliationProcessorService!!.calculateAverageRestaurantRatings(actualRating!!, ratings)
        Assert.assertEquals(3.0, calculatedValue, delta)
    }

    @Test
    fun calculateCachedAverageRestaurantRatings() {
        val cachedUnitAverage = givenUnitAverageStoredInCache()
        val calculatedValue = avaliationProcessorService!!.calculateCachedAverageRestaurantRatings(actualRating!!, cachedUnitAverage)
        Assert.assertEquals(3.5, calculatedValue, delta)
    }

    private fun givenActualRating() : Rating {
        return Rating(UUIDs.timeBased(), 3.0, 1, 1, 1, "Test Comment!",
                Instant.now(), true, RangeTime.UNTIL_FIFTEEN_MINUTES, ImprovementType.ATTENDIMENT)
    }

    private fun givenRatings() : List<RatingDTO> {
        return listOf(RatingDTO(UUIDs.timeBased(), 2.5, 1, UserDTO(1, "Test Name"), "Test Comment 2", Instant.now()),
                RatingDTO(UUIDs.timeBased(), 3.5, 1, UserDTO(1, "Test Name"), "Test Comment 3", Instant.now()))
    }

    private fun givenUnitAverageStoredInCache() : UnitAverageDTO {
        return UnitAverageDTO(4.0, 1)
    }

}
