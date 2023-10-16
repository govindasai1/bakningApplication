package com.example.reposotory

import com.example.dao.BankingDao
import com.example.database.DatabaseFactory
import com.example.methods.*
import com.example.models.*
import com.example.responces.*
import com.example.tables.AccountTable
import com.example.tables.TransactionTable
import com.example.tables.UsersTable
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.update



class BankingDaoImp:BankingDao {
    override suspend fun accountCreation(user: User): Message {
        val random = (123443413..999999999).random()
        DatabaseFactory.dbQuery {
            UsersTable.insert {
                it[userName] = user.userName
                it[password] = user.password
                it[email] = user.password
                it[phoneNumber] = user.phoneNumber
            }
        }
        val userI = gettingUserId(user.userName)
        DatabaseFactory.dbQuery {
            AccountTable.insert {
                it[userId] = userI
                it[accountNumber] = random.toString()
                it[balance] = 0.0F

            }
        }
        return successfulCreation
    }

    override suspend fun login(details: LoginDetails): Id? {
       return DatabaseFactory.dbQuery { UsersTable.select{(UsersTable.userName.eq(details.userName) and UsersTable.password.eq(details.password))}.map { rowToIt(it) } }.singleOrNull()
    }

    override suspend fun retrieveAccBal(id: Int): Message {
        return if (checkingId(id)){
            val balance = DatabaseFactory.dbQuery { AccountTable.select { (AccountTable.userId.eq(id)) }.map { rowToBalance(it) } }.single()
            Message("BALANCE OF USER ID $id IS $balance")
        } else
            failureResponce
    }

    override suspend fun depositFunds(id: Int, amount: Float ): Message {
        return if (checkingId(id)){
            val bal = balance(id)
            DatabaseFactory.dbQuery { AccountTable.update({AccountTable.userId.eq(id)}){
                it[balance] = bal.balance+amount
            } }
            val c =gettingAccId(id)
            DatabaseFactory.dbQuery { TransactionTable.insert {
                it[accountId] = c.id
                it[transactionType] = credit
                it[this.amount] = amount
                it[transactionDate]  = currentDateTime()
            } }
            return successfulDeposit
        }
        else
            failureResponce
    }

    override suspend fun withdrawFunds(id: Int, amount: Float): Message {
        return if (checkingId(id)) {
            val balance = balance(id)
            if (balance.balance == 0.0f && balance.balance < amount) {
                return insufficientFunds
            } else {
            DatabaseFactory.dbQuery {
                AccountTable.update({ AccountTable.userId.eq(id) }) {
                    it[AccountTable.balance] = balance.balance - amount
                }
            }
            val c = gettingAccId(id)
            DatabaseFactory.dbQuery {
                TransactionTable.insert {
                    it[accountId] = c.id
                    it[transactionType] = debit
                    it[this.amount] = amount
                    it[transactionDate] = currentDateTime()
                }
            }
                successfulWithdraw
        }
        }
        else
            failureResponce
    }

    override suspend fun transferFund(sourceId: Int, destinationId: Int,amount : Float): Message {
        return if(checkingId(sourceId) && checkingId(destinationId)){
            withdrawFunds(sourceId,amount)
            depositFunds(destinationId,amount)
            Message("FUNDS TRANSFER FROM SOURCE ID $sourceId TO DESTINATION ID $destinationId SUCCESSFUL")
        }
        else
            failureResponce
    }

    override suspend fun transactionHistory(id: Int):List<Transactions> {
        val accountId = gettingAccId(id)
        return DatabaseFactory.dbQuery { TransactionTable.select { (TransactionTable.accountId.eq(accountId.id)) }.map { rowTransactions(it) } }
    }
}