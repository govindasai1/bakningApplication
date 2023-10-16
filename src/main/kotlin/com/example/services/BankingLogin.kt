package com.example.services

import com.example.dao.BankingDao
import com.example.models.Id
import com.example.models.LoginDetails
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class BankingLogin:KoinComponent {
    private val repo by inject<BankingDao>()

    suspend fun loggingIn(details:LoginDetails): Id?{
        return repo.login(details)
    }
}