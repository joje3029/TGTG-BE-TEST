package com.tgtg.controller

import com.tgtg.dto.request.TestRequest
import com.tgtg.dto.response.TestResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class TestController {
    
    @PostMapping("/test")
    fun test(@RequestBody request: TestRequest): TestResponse {
        return TestResponse(
            message = "사용자명 ${request.username}, 비밀번호 ${request.password} 가 입력되었습니다.",
            username = request.username,
            password = request.password
        )
    }
} 