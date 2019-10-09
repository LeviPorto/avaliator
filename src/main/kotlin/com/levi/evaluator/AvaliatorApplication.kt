package com.levi.evaluator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
@EnableFeignClients
@EnableAsync
class EvaluatorApplication

fun main(args: Array<String>) {
    runApplication<EvaluatorApplication>(*args)
}
