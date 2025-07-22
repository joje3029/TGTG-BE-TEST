package com.tgtg

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
@RestController //rest APi 하기 위한 어노테이션
class TgtgHexagonalApplication {
    
    @GetMapping("/hello") //get 방식
    fun hello(): String {
        println("Hello World Kotlin! - Console Message")  // 콘솔에 출력
        return "Hello World Kotlin! - Browser Message"    // 브라우저에 출력
    }
}

fun main(args: Array<String>) {
    println("Application Starting...") // 애플리케이션 시작 시 콘솔에 출력
    runApplication<TgtgHexagonalApplication>(*args) //Main이 실행되면 class TgtgHexagonalApplication이 되고, 그 안의 함수인 hello가 실행
}
