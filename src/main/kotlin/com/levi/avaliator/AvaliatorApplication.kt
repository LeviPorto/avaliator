package com.levi.avaliator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class AvaliatorApplication

fun main(args: Array<String>) {
	runApplication<AvaliatorApplication>(*args)
}
