package com.example.testDatabase


import org.jetbrains.exposed.sql.Database


object TestDatabaseFactory {
    fun init():Database{
        val url = "jdbc:postgresql://localhost:8080/testBanking"
        val driver = "org.postgresql.Driver"
        val userName = "postgres"
        val password = "root"

        return Database.connect(url, driver, userName, password)

    }

}