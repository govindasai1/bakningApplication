package com.example.di

import com.example.dao.BankingDao
import com.example.reposotory.BankingDaoImp
import com.example.services.BankingRegistation
import com.example.services.BankingLogin
import com.example.services.BankingServices
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val koinModule = module {
    singleOf(::BankingDaoImp){bind<BankingDao>()}
    singleOf(::BankingRegistation)
    singleOf(::BankingLogin)
    singleOf(::BankingServices)
//single<BankingDao> {BankingDaoImp()}
//    single { BankingServices() }
}