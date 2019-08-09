package com.levi.avaliator.dispatcher

import com.levi.avaliator.dtos.AvaliatedRestaurantDTO
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component
import java.text.ParseException

@Component
class RateDispatcher(private val kafkaTemplate: KafkaTemplate<String, AvaliatedRestaurantDTO>) {

    @Value("\${spring.kafka.topic.rate}")
    var topicRate: String? = null

    @Throws(ParseException::class)
    fun sendRateToTopic(avaliatedRestaurantDTO: AvaliatedRestaurantDTO) {
        val message = MessageBuilder
                .withPayload(avaliatedRestaurantDTO)
                .setHeader(KafkaHeaders.TOPIC, topicRate)
                .build()
        kafkaTemplate.send(message)
    }

}