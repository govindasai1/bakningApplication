@file:Suppress("UNUSED_PARAMETER")

package com.example

import com.example.database.DatabaseFactory
import com.example.plugins.*
import io.ktor.server.application.*


fun main(args:Array<String>):Unit = io.ktor.server.netty.EngineMain.main(args)
//fun main() {
//    embeddedServer(Netty, port = 800, host = "0.0.0.0", module = Application::module)
//        .start(wait = true)
//}

fun Application.module(configureRouting: Unit) {
    DatabaseFactory.init()
    koin()
    configureSerialization()
    configureRouting()
}
