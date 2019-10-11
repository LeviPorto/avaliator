package com.levi.evaluator.config

import com.levi.evaluator.dto.EvaluatedRestaurantDTO
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.serializer.JsonSerializer
import java.util.HashMap

@Configuration
class KafkaProducerConfiguration {

    @Value("\${spring.kafka.bootstrap-servers}")
    val bootstrapServer: String? = null

    @Bean
    fun producerConfigs(): Map<String, Any> {
        val props = HashMap<String, Any>()
        props[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapServer!!
        props[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        props[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = JsonSerializer::class.java
        props[JsonSerializer.ADD_TYPE_INFO_HEADERS] = false
        return props
    }

    @Bean
    fun producerFactory(): ProducerFactory<String, EvaluatedRestaurantDTO> {
        return DefaultKafkaProducerFactory(producerConfigs())
    }

    @Bean
    fun kafkaTemplate(): KafkaTemplate<String, EvaluatedRestaurantDTO> {
        return KafkaTemplate(producerFactory())
    }
}
