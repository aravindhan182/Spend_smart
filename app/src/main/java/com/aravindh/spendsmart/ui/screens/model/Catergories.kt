package com.aravindh.spendsmart.ui.screens.model

import androidx.compose.ui.graphics.vector.ImageVector
import com.aravindh.spendsmart.data.expense.ExpenseCategory
import com.aravindh.spendsmart.data.income.IncomeCategory

data class ExpenseCategoryView(
    var catergoryName: List<ExpenseCategory>,
    var categoryImage: List<ImageVector>
)

data class IncomeCategoryView(
    var catergoryName: List<IncomeCategory>,
    var categoryImage: List<ImageVector>
)
