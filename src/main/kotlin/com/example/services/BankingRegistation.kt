package com.example.services

import com.example.dao.BankingDao
import com.example.models.Message
import com.example.models.User
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class BankingRegistation:KoinComponent {
    private val repo by inject<BankingDao>()

    suspend fun creatingAccount(user: User):Message{
        return repo.accountCreation(user)
    }

}