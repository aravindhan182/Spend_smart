package com.aravindh.spendsmart.ui.screens.model

import androidx.compose.ui.graphics.vector.ImageVector

data class ExpenseOrIncomeMutableView(
    var id: Int,
    var title: String,
    var value:String,
    var notes:String,
    var category: ImageVector,
    var paymentMethod: String,
    var date:String,
    var time:String
)
