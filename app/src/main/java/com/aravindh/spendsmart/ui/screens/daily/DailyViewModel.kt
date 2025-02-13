package com.aravindh.spendsmart.ui.screens.daily

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import com.aravindh.spendsmart.data.expense.TransactionRepository

@RequiresApi(Build.VERSION_CODES.O)
class DailyViewModel(application: Application) : AndroidViewModel(application) {

    val transactionRepository = TransactionRepository.getInstance(application)
    val allTransactions = transactionRepository.getTransactions()
}