package com.example.testRoute

import com.example.module
import com.example.plugins.configureRouting
import com.example.testParameters.details
import com.example.testParameters.user
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class BankingRouteTest {

    @Test
    fun testInsert() = testApplication {
        application {
            module(configureRouting())
        }
        val client = createClient {
            install(ContentNegotiation){
                json()
            }
        }
        val responce = client.post("/Register") {
            headers[HttpHeaders.ContentType] = ContentType.Application.Json.toString()
            setBody(user)
        }
        assertEquals(HttpStatusCode.OK,responce.status)
    }

    @Test
    fun testLogin() = testApplication {
        application {
            module(configureRouting())
        }
        val client = createClient {
            install(ContentNegotiation){
                json()
            }
        }
        val responce = client.post("/Login") {
            headers[HttpHeaders.ContentType] = ContentType.Application.Json.toString()
            setBody(details)
        }
        assertEquals(HttpStatusCode.OK,responce.status)
    }


}