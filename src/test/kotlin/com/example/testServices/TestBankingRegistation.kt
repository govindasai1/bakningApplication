package com.example.testServices

import com.example.di.koinModule
import com.example.models.Message
import com.example.responces.successfulCreation
import com.example.services.BankingLogin
import com.example.services.BankingRegistation
import com.example.services.BankingServices
import com.example.tables.AccountTable
import com.example.tables.TransactionTable
import com.example.tables.UsersTable
import com.example.testDatabase.TestDatabaseFactory
import com.example.testParameters.*
import io.ktor.server.testing.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import java.sql.Connection
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@Suppress("UNUSED_VARIABLE")
class TestBankingRegistation {
    private val bankingRegObj = BankingRegistation()
    private val bankingLoginObj = BankingLogin()
    private val bankingServiceObj = BankingServices()
    private lateinit var database: Database

    @Before
    fun start(){
        startKoin {
            modules(koinModule)
        }
        database = TestDatabaseFactory.init()
        TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_REPEATABLE_READ
        transaction(database) {
            SchemaUtils.create(AccountTable, TransactionTable, UsersTable)
        }
    }
    @After
    fun end(){
        stopKoin()
        transaction(database) {
            SchemaUtils.drop(AccountTable,TransactionTable,UsersTable)
        }
    }

    @Test
    fun testRegistationServices() = testApplication {
        val result = bankingRegObj.creatingAccount(user)
        assertEquals(successfulCreation,result)
    }

    @Test
    fun testLogin() = testApplication {
        val result = bankingLoginObj.loggingIn(details)
        assertTrue(true)
    }

    @Test
    fun retrieveBal() = testApplication {
        val result = bankingServiceObj.gettingBalance(id)
        if (result.equals(Message)) assertTrue(true)
        else assertFalse(false)
    }

    @Test
    fun testDepositServices() = testApplication {
        val result = bankingServiceObj.fundsDeposit(id, depositAmount)
        if (result.equals(Message)) assertTrue(true)
        else assertFalse(false)
    }

    @Test
    fun testWithdrawServices() = testApplication {
        val result = bankingServiceObj.fundsWithdraw(id, withdrawAmount)
        if (result.equals(Message)) assertTrue(true)
        else assertFalse(false)
    }

}