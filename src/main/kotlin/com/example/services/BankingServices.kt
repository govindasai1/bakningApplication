package com.example.services

import com.example.dao.BankingDao
import com.example.models.Message
import com.example.models.Transactions
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class BankingServices:KoinComponent {
    private val repo by inject<BankingDao>()

    suspend fun gettingBalance(id:Int):Message{
        return repo.retrieveAccBal(id)
    }
    suspend fun fundsDeposit(id: Int,amount: Float):Message{
        return repo.depositFunds(id,amount)
    }
    suspend fun fundsWithdraw(id: Int,amount: Float):Message{
        return repo.withdrawFunds(id, amount)
    }
    suspend fun fundTransfer(sourceId: Int,destinationId: Int,amount: Float):Message{
        return repo.transferFund(sourceId,destinationId,amount)
    }
    suspend fun historyOfTransaction(id: Int): List<Transactions>{
        return repo.transactionHistory(id)
    }
}