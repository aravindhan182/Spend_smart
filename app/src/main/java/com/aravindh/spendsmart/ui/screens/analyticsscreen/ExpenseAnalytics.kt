package com.aravindh.spendsmart.ui.screens.analyticsscreen

import com.aravindh.spendsmart.data.expense.ExpenseCategory

data class ExpenseAnalytics(
    val category: ExpenseCategory,
    val percentage: Float
)
