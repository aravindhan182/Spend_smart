package com.aravindh.spendsmart.ui.screens.transactionscreen

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aravindh.spendsmart.data.expense.ExpenseCategory
import com.aravindh.spendsmart.data.expense.IncomeCategory
import com.aravindh.spendsmart.data.expense.PaymentMethod
import com.aravindh.spendsmart.data.expense.Transaction
import com.aravindh.spendsmart.data.expense.TransactionRepository
import com.aravindh.spendsmart.data.expense.TransactionType
import com.aravindh.spendsmart.ui.screens.model.TransactionMutableErrorView
import com.aravindh.spendsmart.ui.screens.model.TransactionMutableView
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

@RequiresApi(Build.VERSION_CODES.O)
class TransactionViewModel(application: Application) : AndroidViewModel(application) {

    private val transactionRepository: TransactionRepository =
        TransactionRepository.getInstance(application)

    private val _transactionData = MutableLiveData(
        TransactionMutableView(
            transactionType = TransactionType.INCOME,
            amount = "",
            notes = ""
        )
    )
    val transactionData: LiveData<TransactionMutableView> = _transactionData

    private val _saved: MutableLiveData<Boolean> = MutableLiveData(false)
    val saved: LiveData<Boolean> = _saved

    private val _transactionDataError = MutableLiveData(TransactionMutableErrorView())
    val transactionDataError: LiveData<TransactionMutableErrorView> = _transactionDataError

    fun getTransactionData(transactionID: String) {
        viewModelScope.launch {
            val transactionDetail = transactionRepository.getTransactionDetailById(transactionID)
            _transactionData.value = TransactionMutableView(
                transactionID = transactionDetail.id,
                transactionType = transactionDetail.transactionType,
                createdDate = transactionDetail.createdDate,
                createdTime = transactionDetail.createdTime,
                amount = transactionDetail.amount.toString(),
                notes = transactionDetail.notes,
                incomeCategory = transactionDetail.incomeCategory,
                expenseCategory = transactionDetail.expenseCategory,
                paymentMethod = transactionDetail.paymentMethod,
            )
        }
    }



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

    fun saveTransaction(isUpdate: Boolean = false) {
        viewModelScope.launch {
            val transactionDataView = _transactionData.value!!
            when {
                transactionDataView.amount.isEmpty() -> {
                    _transactionDataError.value =
                        TransactionMutableErrorView(amountError = "Please enter the amount")
                }

                else -> {
                    if (isUpdate) {
                        _transactionDataError.value = TransactionMutableErrorView()
                        transactionRepository.updateTransaction(
                            Transaction(
                                id = transactionDataView.transactionID!!,
                                transactionType = transactionDataView.transactionType,
                                createdDate = transactionDataView.createdDate,
                                createdTime = transactionDataView.createdTime,
                                amount = transactionDataView.amount.toDouble(),
                                incomeCategory = transactionDataView.incomeCategory,
                                expenseCategory = transactionDataView.expenseCategory,
                                paymentMethod = transactionDataView.paymentMethod,
                                notes = transactionDataView.notes
                            )
                        )
                        _saved.value = true
                    } else {
                        _transactionDataError.value = TransactionMutableErrorView()
                        val uniqueId: String = UUID.randomUUID().toString()
                        transactionRepository.addTransaction(
                            Transaction(
                                id = uniqueId,
                                transactionType = transactionDataView.transactionType,
                                createdDate = transactionDataView.createdDate,
                                createdTime = transactionDataView.createdTime,
                                amount = transactionDataView.amount.toDouble(),
                                incomeCategory = transactionDataView.incomeCategory,
                                expenseCategory = transactionDataView.expenseCategory,
                                paymentMethod = transactionDataView.paymentMethod,
                                notes = transactionDataView.notes
                            )
                        )
                        _saved.value = true
                    }
                }
            }
        }
    }
    fun resetSavedState() {
        _saved.value = false
        _transactionDataError.value = TransactionMutableErrorView()
    }
}