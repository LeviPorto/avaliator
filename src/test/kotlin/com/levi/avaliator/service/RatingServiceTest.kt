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
import com.levi.avaliator.repository.RatingRepositoryTest
import org.junit.Assert
import org.junit.Before
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

//TODO por valores em constantes

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

    private var uuid : UUID? = null
    private var instant : Instant? = null

    @Before
    fun setUp() {
        uuid = UUIDs.timeBased()
        instant = Instant.now()
    }

    @Test
    fun create() {
        BDDMockito.given(repository!!.save(Mockito.any(Rating::class.java))).willReturn(givenRating())
        val createdRating = service!!.create(givenRating())
        Assert.assertNotNull(createdRating)
    }

    @Test
    fun retrieveRestaurantRatings() {
        BDDMockito.given(repository!!.findByRestaurantId(1)).willReturn(givenRestaurantRatings())
        BDDMockito.given(managerApi!!.retrieveByIds(listOf(1, 2))).willReturn(givenAvaliators())
        val restaurantRatingsDTO = service!!.retrieveRestaurantRatings(1)
        Assert.assertEquals(restaurantRatingsDTO, givenRestaurantRatingsDTO())
    }

    private fun givenRestaurantRatings() : List<Rating> {
        return listOf(Rating(uuid!!, 3.0, 1, 1, 1, "Test Comment!",
                instant!!, true, RangeTime.UNTIL_FIFTEEN_MINUTES, ImprovementType.ATTENDIMENT), Rating(UUIDs.timeBased(), 1.0, 1, 1, 2, "Test Comment!",
                instant!!, true, RangeTime.UNTIL_FIFTEEN_MINUTES, ImprovementType.ATTENDIMENT))
    }

    private fun givenRating() : Rating {
        return Rating(uuid!!, 3.0, 1, 1, 1, "Test Comment!",
                instant!!, true, RangeTime.UNTIL_FIFTEEN_MINUTES, ImprovementType.ATTENDIMENT)
    }

    private fun givenAvaliators() : List<UserDTO> {
        return listOf(UserDTO(1, "Test Name 1"), UserDTO(2, "Test Name 2"))
    }

    private fun givenRestaurantRatingsDTO() : List<RatingDTO> {
        return listOf(RatingDTO(uuid!!, 3.0, 1, UserDTO(1, "Test Name 1"),
                "Test Comment!", instant!!), RatingDTO(uuid!!, 1.0, 1,
                UserDTO(2, "Test Name 2"), "Test Comment!", instant!!))
    }

}
