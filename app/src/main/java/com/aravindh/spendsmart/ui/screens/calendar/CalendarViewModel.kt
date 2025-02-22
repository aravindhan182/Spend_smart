package com.aravindh.spendsmart.ui.screens.calendar

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aravindh.spendsmart.data.expense.Transaction
import com.aravindh.spendsmart.data.expense.TransactionRepository
import kotlinx.coroutines.launch
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
class CalendarViewModel(application: Application) : AndroidViewModel(application) {

    private val transactionRepository = TransactionRepository.getInstance(application)

    private val _dateRange = MutableLiveData(MutableDateRange())
    val dateRange: LiveData<MutableDateRange> = _dateRange

    fun getDataByDateRange(fromDate: LocalDate, toDate: LocalDate): LiveData<List<Transaction>> {
        return transactionRepository.getDataByDateRange(fromDate, toDate)
    }

    fun deleteTransaction(id: String) {
        viewModelScope.launch {
            transactionRepository.deleteById(id)
        }
    }
}