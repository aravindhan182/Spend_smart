package com.aravindh.spendsmart.ui.screens.analyticsscreen

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aravindh.spendsmart.data.expense.Transaction
import com.aravindh.spendsmart.data.expense.TransactionRepository
import com.aravindh.spendsmart.data.expense.TransactionType
import com.aravindh.spendsmart.ui.screens.calendar.MutableDateRange
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
class AnalyticViewModel(application: Application) : AndroidViewModel(application) {
    private val transactionRepository = TransactionRepository.getInstance(application)
    val todayExpense =
        transactionRepository.getTodayExpenseTransaction(LocalDate.now(), TransactionType.EXPENSE)

    private val _dateRange = MutableLiveData(MutableDateRange())
    val dateRange: LiveData<MutableDateRange> = _dateRange

    fun getDataByDateRange(
        transactionType: TransactionType,
        fromDate: LocalDate,
        toDate: LocalDate
    ): LiveData<List<Transaction>> {
        return transactionRepository.getExpensesByDateRange(transactionType, fromDate, toDate)
    }
}