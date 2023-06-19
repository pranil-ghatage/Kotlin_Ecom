package com.example.eCommerce

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloWorldController {

    @GetMapping
    fun helloWorld() : String {
        return "Kotlin spring boot API";
    }
}