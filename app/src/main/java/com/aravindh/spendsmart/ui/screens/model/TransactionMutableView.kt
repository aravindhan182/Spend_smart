package com.aravindh.spendsmart.ui.screens.model

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.vector.ImageVector
import com.aravindh.spendsmart.data.expense.ExpenseCategory
import com.aravindh.spendsmart.data.expense.IncomeCategory
import com.aravindh.spendsmart.data.expense.PaymentMethod
import com.aravindh.spendsmart.data.expense.TransactionType
import java.time.LocalDate
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
data class TransactionMutableView(
    var transactionID:String? = null,
    var transactionType: TransactionType = TransactionType.INCOME,
    var amount: String = "",
    var notes: String = "",
    var incomeCategory: IncomeCategory? = IncomeCategory.SALE,
    var incomeCategoryImage: ImageVector? = null,
    var expenseCategory: ExpenseCategory? = ExpenseCategory.FOOD,
    var expenseCategoryImage: ImageVector? = null,
    var paymentMethod: PaymentMethod? = null,
    var createdDate: LocalDate = LocalDate.now(),
    var createdTime: LocalTime = LocalTime.now()
)

data class TransactionMutableErrorView(
    var amountError: String? = null,
    var incomeCategoryError: String? = null,
    var expenseCategoryError: String? = null
)
