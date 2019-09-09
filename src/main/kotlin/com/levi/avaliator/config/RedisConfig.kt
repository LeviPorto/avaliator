package com.levi.avaliator.config

import com.levi.avaliator.dtos.AverageUnitDTO
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
    fun redisRateTemplate(): RedisTemplate<String,AverageUnitDTO> {
        val template = RedisTemplate<String, AverageUnitDTO>()
        template.setConnectionFactory(redisConnectionFactory())
        template.keySerializer = StringRedisSerializer()
        template.hashValueSerializer = Jackson2JsonRedisSerializer(AverageUnitDTO::class.java)
        template.valueSerializer = Jackson2JsonRedisSerializer(AverageUnitDTO::class.java)
        return template
    }

}
