package com.aravindh.spendsmart.ui.screens.transactionscreen

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aravindh.spendsmart.data.expense.Expense
import com.aravindh.spendsmart.data.expense.ExpenseCategory
import com.aravindh.spendsmart.data.expense.ExpenseRepository
import com.aravindh.spendsmart.data.expense.PaymentMethod
import com.aravindh.spendsmart.data.income.Income
import com.aravindh.spendsmart.data.income.IncomeCategory
import com.aravindh.spendsmart.data.income.IncomeRepository
import com.aravindh.spendsmart.data.income.TransactionType
import com.aravindh.spendsmart.ui.screens.model.TransactionMutableErrorView
import com.aravindh.spendsmart.ui.screens.model.TransactionMutableView
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

@RequiresApi(Build.VERSION_CODES.O)
class TransactionViewModel(application: Application) : AndroidViewModel(application) {

    private val incomeRepository: IncomeRepository = IncomeRepository.getInstance(application)
    private val expenseRepository: ExpenseRepository = ExpenseRepository.getInstance(application)

    private val _transactionData = MutableLiveData(
        TransactionMutableView(
            transactionType = TransactionType.INCOME,
            amount = "",
            notes = ""
        )
    )
    val transactionData: LiveData<TransactionMutableView> = _transactionData

    private val _transactionDataError = MutableLiveData(TransactionMutableErrorView())
    val transactionDataError: LiveData<TransactionMutableErrorView> = _transactionDataError

    fun updateTransactionType(type: TransactionType) {
        _transactionData.value = _transactionData.value?.copy(transactionType = type)
    }

    fun updateAmount(newAmount: String) {
        _transactionData.value = _transactionData.value?.copy(amount = newAmount)
    }

    fun updateNotes(newNotes: String) {
        _transactionData.value = _transactionData.value?.copy(notes = newNotes)
    }

    fun updateCreatedDate(date: LocalDate) {
        _transactionData.value = _transactionData.value?.copy(createdDate = date)
    }

    fun updateExpenseCategory(type: ExpenseCategory) {
        _transactionData.value = _transactionData.value?.copy(expenseCategory = type)
    }

    fun updateIncomeCategory(type: IncomeCategory) {
        _transactionData.value = _transactionData.value?.copy(incomeCategory = type)
    }

    fun updatePaymentMethod(type: PaymentMethod) {
        _transactionData.value = _transactionData.value?.copy(paymentMethod = type)
    }

    fun updateCreatedTime(time: LocalTime) {
        _transactionData.value = _transactionData.value?.copy(createdTime = time)
    }

    fun saveTransaction() {
        viewModelScope.launch {
            val transactionDataView = _transactionData.value

            if (transactionDataView?.transactionType == TransactionType.INCOME) {
                when {
                    transactionDataView?.incomeCategory == null -> {
                        _transactionDataError.value =
                            TransactionMutableErrorView(amountError = "Please select transaction type")
                    }
                    transactionDataView.amount.isEmpty() -> {
                        _transactionDataError.value =
                            TransactionMutableErrorView(amountError = "Please enter the amount")
                    }

                    else -> {
                        _transactionDataError.value = TransactionMutableErrorView()
                        val uniqueId: String = UUID.randomUUID().toString()
                        incomeRepository.addIncome(
                            Income(
                                id = uniqueId,
                                transactionType = transactionDataView.transactionType,
                                createdDate = transactionDataView.createdDate,
                                createdTime = transactionDataView.createdTime,
                                amount = transactionDataView.amount.toDouble(),
                                incomeCategory = transactionDataView.incomeCategory,
                                notes = transactionDataView.notes
                            )
                        )
                    }
                }
            } else {
                val uniqueId: String = UUID.randomUUID().toString()
                when {
                    transactionDataView?.expenseCategory == null -> {
                        _transactionDataError.value =
                            TransactionMutableErrorView(amountError = "Please select transaction type")
                    }

                    transactionDataView.amount.isEmpty() -> {
                        _transactionDataError.value =
                            TransactionMutableErrorView(amountError = "Please enter the amount")
                    }

                    else -> {
                        _transactionDataError.value = TransactionMutableErrorView()
                        expenseRepository.addExpense(
                            Expense(
                                id = uniqueId,
                                createdDate = transactionDataView.createdDate,
                                createdTime = transactionDataView.createdTime,
                                transactionType = transactionDataView.transactionType,
                                amount = transactionDataView.amount.toDouble(),
                                expenseCategory = transactionDataView.expenseCategory,
                                paymentMethod = transactionDataView.paymentMethod!!,
                                notes = transactionDataView.notes
                            )
                        )
                    }
                }
            }
        }
    }
}