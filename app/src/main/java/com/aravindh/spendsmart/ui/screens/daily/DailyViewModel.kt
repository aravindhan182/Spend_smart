package com.aravindh.spendsmart.ui.screens.daily

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.aravindh.spendsmart.data.expense.TransactionRepository
import com.aravindh.spendsmart.data.expense.TransactionType
import kotlinx.coroutines.launch
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
class DailyViewModel(application: Application) : AndroidViewModel(application) {

    private val transactionRepository = TransactionRepository.getInstance(application)
    val allTransactions = transactionRepository.getTransactions()
    val todayExpense = transactionRepository.getTodayExpense(LocalDate.now(), TransactionType.EXPENSE)
    val todayIncome = transactionRepository.getTodayIncome(LocalDate.now(), TransactionType.INCOME)

    fun deleteTransaction(id:String) {
        viewModelScope.launch {
            transactionRepository.deleteById(id)
        }
    }
}