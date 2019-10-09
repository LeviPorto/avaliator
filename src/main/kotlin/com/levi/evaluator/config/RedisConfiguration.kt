package com.levi.evaluator.config

import com.levi.evaluator.dto.UnitAverageDTO
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.*

@Configuration
class RedisConfiguration {

    @Value("\${spring.redis.port}")
    var port: Int? = null
    @Value("\${spring.redis.host}")
    var host: String? = null

    @Bean
    fun redisConnectionFactory(): JedisConnectionFactory {
        val config = RedisStandaloneConfiguration(host!!, port!!)
        return JedisConnectionFactory(config)
    }

    @Bean
    fun redisRatingTemplate(): RedisTemplate<String, UnitAverageDTO> {
        val template = RedisTemplate<String, UnitAverageDTO>()
        template.setConnectionFactory(redisConnectionFactory())
        template.keySerializer = StringRedisSerializer()
        template.hashValueSerializer = Jackson2JsonRedisSerializer(UnitAverageDTO::class.java)
        template.valueSerializer = Jackson2JsonRedisSerializer(UnitAverageDTO::class.java)
        return template
    }

}
