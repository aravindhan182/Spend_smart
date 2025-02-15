package com.aravindh.spendsmart.ui.screens.montlyscreen

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.aravindh.spendsmart.data.expense.Transaction
import com.aravindh.spendsmart.data.expense.TransactionRepository
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class MonthlyViewModel(application: Application) : AndroidViewModel(application) {

    private val transactionRepository = TransactionRepository.getInstance(application)

    fun deleteTransaction(id: String) {
        viewModelScope.launch {
            transactionRepository.deleteById(id)
        }
    }

    fun getDataByYearAndMonth(yearAndMonth: String): LiveData<List<Transaction>> {
        return transactionRepository.getDataByYearMonth(yearAndMonth)
    }
}