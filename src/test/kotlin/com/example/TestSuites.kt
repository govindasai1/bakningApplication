package com.example

import com.example.testReposotory.TestBankingDaoImp
import com.example.testRoute.BankingRouteTest
import com.example.testServices.TestBankingRegistation
import org.junit.runner.RunWith
import org.junit.runners.Suite


@RunWith(Suite::class)
@Suite.SuiteClasses(
    TestBankingDaoImp::class,
    TestBankingRegistation::class,
    BankingRouteTest::class
)
class TestSuites
