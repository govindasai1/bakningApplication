package com.example.responces

import com.example.models.Message
import com.example.tables.TransactionTable.amount
import com.example.tables.TransactionTable.id

val successfulCreation = Message("USER ACCOUNT CREATED SUCCESSFULLY")
val failureResponce = Message("ACCOUNT NOT FOUND")
val successfulDeposit = Message("$amount DEPOSITED SUCCESSFULLY FOR USER ID $id")
val insufficientFunds = Message("AMOUNT NOT SUFFICIENT TO WITHDRAW ")
val successfulWithdraw = Message("AMOUNT WITHDRAWN FOR USER ID $id SUCCESSFUL")
