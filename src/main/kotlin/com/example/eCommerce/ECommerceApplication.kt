package com.example.eCommerce

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
class ECommerceApplication

fun main(args: Array<String>) {
	println("runing ecommerce")
	runApplication<ECommerceApplication>(*args)
	println("runing ecommerce")

}
