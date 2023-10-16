package com.example.testReposotory

import com.example.models.Message
import com.example.reposotory.BankingDaoImp
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
import java.sql.Connection
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class TestBankingDaoImp {
    private lateinit var database: Database
    private val obj = BankingDaoImp()

    @Before
    fun start(){
        database = TestDatabaseFactory.init()
        TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_REPEATABLE_READ
        transaction(database) {
            SchemaUtils.create(AccountTable,TransactionTable,UsersTable)
        }
    }

    @After
    fun end(){
        transaction(database) {
            SchemaUtils.drop(AccountTable,TransactionTable,UsersTable)
        }
    }

    @Test
    fun testAccountCreation() = testApplication {
        val result =obj.accountCreation(user)
        if (result.equals(Message)) assertTrue(true)
        else assertFalse(false)
    }

    @Test
    fun testAccountCreation1() = testApplication {
        val result =obj.accountCreation(user1)
        if (result.equals(Message)) assertTrue(true)
        else assertFalse(false)
    }

    @Test
    fun testRetrieveBalance() = testApplication {
        val result = obj.retrieveAccBal(id)
        if (result.equals(Message)) assertTrue(true)
        else assertFalse(false)
    }

    @Test
    fun testDepositFunds() = testApplication {
        val result = obj.depositFunds(id, depositAmount)
        if (result.equals(Message)) assertTrue(true)
        else assertFalse(false)
    }

    @Test
    fun testWithdrawFunds() = testApplication {
        val result = obj.withdrawFunds(id,withdrawAmount)
        if (result.equals(Message)) assertTrue(true)
        else assertFalse(false)
    }

    @Test
    fun testTransferFunds() = testApplication {
        val result = obj.transferFund(id,id1, transferAmount)
        if (result.equals(Message)) assertTrue(true)
        else assertFalse(false)
    }

}