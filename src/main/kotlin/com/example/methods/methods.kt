package com.example.methods

import com.example.database.DatabaseFactory
import com.example.models.Balance
import com.example.models.Id
import com.example.models.Transactions
import com.example.tables.AccountTable
import com.example.tables.TransactionTable
import com.example.tables.UsersTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.select
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

const val credit = "Credit"
const val debit = "Debit"
fun rowToIt(row: ResultRow): Id {
    return Id(row[UsersTable.id].value)
}

fun rowToBalance(row: ResultRow): Balance {
    return Balance(row[AccountTable.balance])
}

fun rowToAccId(row: ResultRow): Id {
    return Id(row[AccountTable.id].value)
}

fun rowTransactions(row: ResultRow): Transactions {
    return Transactions(
        row[TransactionTable.accountId].value,
        row[TransactionTable.transactionType],
        row[TransactionTable.amount],
        row[TransactionTable.transactionDate]
    )
}

suspend fun balance(id: Int): Balance {
    return DatabaseFactory.dbQuery { AccountTable.select { (AccountTable.userId.eq(id)) }.map { rowToBalance(it) } }
        .single()
}

suspend fun gettingUserId(userName:String):Int{
    val c = DatabaseFactory.dbQuery { UsersTable.select(UsersTable.userName.eq(userName)).map { rowToIt(it) } }.single()
    return c.id
}

suspend fun checkingId(id: Int): Boolean {
    val result = DatabaseFactory.dbQuery { AccountTable.select { AccountTable.userId.eq(id) }.map { rowToAccId(it) } }
        .singleOrNull()
    return result != null
}

suspend fun gettingAccId(id: Int): Id {
    return DatabaseFactory.dbQuery { AccountTable.select { (AccountTable.userId.eq(id)) }.map { rowToAccId(it) } }
        .single()
}

fun currentDateTime(): String {
    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
    return LocalDateTime.now().format(formatter)

}