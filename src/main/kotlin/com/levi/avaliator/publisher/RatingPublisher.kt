package com.levi.avaliator.publisher

import com.levi.avaliator.dto.AvaliatedRestaurantDTO
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component
import java.text.ParseException

@Component
class RatingPublisher(private val kafkaTemplate: KafkaTemplate<String, AvaliatedRestaurantDTO>) {

    @Value("\${spring.kafka.topic.rating}")
    var topicRating: String? = null

    @Throws(ParseException::class)
    fun sendRatingToTopic(avaliatedRestaurantDTO: AvaliatedRestaurantDTO) {
        val message = MessageBuilder
                .withPayload(avaliatedRestaurantDTO)
                .setHeader(KafkaHeaders.TOPIC, topicRating)
                .build()
        kafkaTemplate.send(message)
    }

}
