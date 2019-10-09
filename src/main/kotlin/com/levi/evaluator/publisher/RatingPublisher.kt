package com.levi.evaluator.publisher

import com.levi.evaluator.dto.EvaluatedRestaurantDTO
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component
import java.text.ParseException

@Component
class RatingPublisher(private val kafkaTemplate: KafkaTemplate<String, EvaluatedRestaurantDTO>) {

    @Value("\${spring.kafka.topic.rating}")
    var topicRating: String? = null

    @Throws(ParseException::class)
    fun sendRatingToTopic(evaluatedRestaurantDTO: EvaluatedRestaurantDTO) {
        val message = MessageBuilder
                .withPayload(evaluatedRestaurantDTO)
                .setHeader(KafkaHeaders.TOPIC, topicRating)
                .build()
        kafkaTemplate.send(message)
    }

}
