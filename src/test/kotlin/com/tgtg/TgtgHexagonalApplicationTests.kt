package com.tgtg.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.tgtg.dto.request.TestRequest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
class TestControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `should call test API and return expected response`() {
        val request = TestRequest("testUser", "testPass")

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/test")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ObjectMapper().writeValueAsString(request))
        )
        .andExpect(MockMvcResultMatchers.status().isOk)
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
        .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("testUser"))
    }
}