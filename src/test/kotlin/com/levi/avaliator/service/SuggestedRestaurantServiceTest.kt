package com.levi.avaliator.service

import com.datastax.driver.core.utils.UUIDs
import com.levi.avaliator.AvaliatorApplication
import com.levi.avaliator.entity.SuggestedRestaurant
import com.levi.avaliator.repository.SuggestedRestaurantRepository
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

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [AvaliatorApplication::class])
@ActiveProfiles("test")
class SuggestedRestaurantServiceTest {

    @Autowired
    private val service: SuggestedRestaurantService? = null

    @MockBean
    private val repository: SuggestedRestaurantRepository? = null

    //TODO tentar ver melhor esse teste

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

    private fun givenFirstSuggestedRestaurant() : SuggestedRestaurant {
        return SuggestedRestaurant(UUIDs.timeBased(), "Test Restaurant", "Test Address","Test Phone")
    }

    private fun givenSuggestedRestaurant() : SuggestedRestaurant {
        return SuggestedRestaurant(UUIDs.timeBased(), "Test Restaurant", "Test Address", 3, "Test Phone")
    }

}
