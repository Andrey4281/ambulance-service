package ru.ambulance.gateway.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SystemController {

    @RequestMapping("/circuitbreakerfallback")
    fun circuitbreakerfallback(): String {
        return "This is a fallback"
    }
}