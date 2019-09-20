package com.levi.avaliator.service

import com.datastax.driver.core.utils.UUIDs
import com.levi.avaliator.AvaliatorApplication
import com.levi.avaliator.api.ManagerApi
import com.levi.avaliator.dto.RatingDTO
import com.levi.avaliator.dto.UserDTO
import com.levi.avaliator.entity.Rating
import com.levi.avaliator.enumeration.ImprovementType
import com.levi.avaliator.enumeration.RangeTime
import com.levi.avaliator.repository.RatingRepository
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
import java.time.Instant
import java.util.*

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [AvaliatorApplication::class])
@ActiveProfiles("test")
class RatingServiceTest {

    @Autowired
    private val service: RatingService? = null

    @MockBean
    private val repository: RatingRepository? = null

    @MockBean
    private val avaliationProcessorService: AvaliationProcessorService? = null

    @MockBean
    private val cachedAvaliationProcessorService: CachedAvaliationProcessorService? = null

    @MockBean
    private val managerApi: ManagerApi? = null

    companion object {
        private val UUID : UUID = UUIDs.timeBased()
        private val INSTANT : Instant = Instant.now()

        private const val RATING_VALUE_ONE = 1.0
        private const val RATING_VALUE_THREE = 3.0
        private const val RATING_RESTAURANT_ID = 1
        private const val RATING_ORDER_ID = 1
        private const val FIRST_USER_ID = 1
        private const val FIRST_USER_NAME = "Test Name 1"
        private const val SECOND_USER_ID = 2
        private const val SECOND_USER_NAME = "Test Name 2"
        private const val RATING_COMMENT = "Test Comment"
    }

    @Test
    fun create() {
        BDDMockito.given(repository!!.save(Mockito.any(Rating::class.java))).willReturn(givenRating())
        val createdRating = service!!.create(givenRating())
        Assert.assertNotNull(createdRating)
    }

    @Test
    fun retrieveRestaurantRatings() {
        BDDMockito.given(repository!!.findByRestaurantId(RATING_RESTAURANT_ID)).willReturn(givenRestaurantRatings())
        BDDMockito.given(managerApi!!.retrieveByIds(listOf(FIRST_USER_ID, SECOND_USER_ID))).willReturn(givenAvaliators())
        val restaurantRatingsDTO = service!!.retrieveRestaurantRatings(RATING_RESTAURANT_ID)
        Assert.assertEquals(restaurantRatingsDTO, givenRestaurantRatingsDTO())
    }

    private fun givenRestaurantRatings() : List<Rating> {
        return listOf(Rating(UUID, RATING_VALUE_THREE, RATING_RESTAURANT_ID, RATING_ORDER_ID, FIRST_USER_ID, RATING_COMMENT,
                INSTANT, true, RangeTime.UNTIL_FIFTEEN_MINUTES, ImprovementType.ATTENDIMENT), Rating(UUIDs.timeBased(),
                RATING_VALUE_ONE, RATING_RESTAURANT_ID, RATING_ORDER_ID, SECOND_USER_ID, RATING_COMMENT,
                INSTANT, true, RangeTime.UNTIL_FIFTEEN_MINUTES, ImprovementType.ATTENDIMENT))
    }

    private fun givenRating() : Rating {
        return Rating(UUID, RATING_VALUE_THREE, RATING_RESTAURANT_ID, RATING_ORDER_ID, FIRST_USER_ID, RATING_COMMENT,
                INSTANT, true, RangeTime.UNTIL_FIFTEEN_MINUTES, ImprovementType.ATTENDIMENT)
    }

    private fun givenAvaliators() : List<UserDTO> {
        return listOf(UserDTO(FIRST_USER_ID, FIRST_USER_NAME), UserDTO(SECOND_USER_ID, SECOND_USER_NAME))
    }

    private fun givenRestaurantRatingsDTO() : List<RatingDTO> {
        return listOf(RatingDTO(UUID, RATING_VALUE_THREE, RATING_RESTAURANT_ID, UserDTO(FIRST_USER_ID, FIRST_USER_NAME),
                RATING_COMMENT, INSTANT), RatingDTO(UUID, RATING_VALUE_ONE, RATING_RESTAURANT_ID,
                UserDTO(SECOND_USER_ID, SECOND_USER_NAME), RATING_COMMENT, INSTANT))
    }

}
