package com.aravindh.spendsmart.ui.screens.daily

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import com.aravindh.spendsmart.data.expense.TransactionRepository
import com.aravindh.spendsmart.data.expense.TransactionType
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
class DailyViewModel(application: Application) : AndroidViewModel(application) {

    val transactionRepository = TransactionRepository.getInstance(application)
    val allTransactions = transactionRepository.getTransactions()
    val todayExpense = transactionRepository.getTodayExpense(LocalDate.now(), TransactionType.EXPENSE)
    val todayIncome = transactionRepository.getTodayIncome(LocalDate.now(), TransactionType.INCOME)
}