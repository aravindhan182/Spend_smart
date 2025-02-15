package com.aravindh.spendsmart.ui.screens.montlyscreen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.aravindh.spendsmart.R
import com.aravindh.spendsmart.data.expense.ExpenseCategory
import com.aravindh.spendsmart.data.expense.IncomeCategory
import com.aravindh.spendsmart.ui.screens.daily.IncomeOrExpenseRecyclerViewCard
import com.aravindh.spendsmart.ui.screens.daily.NorRecordFoundCardView
import com.aravindh.spendsmart.ui.screens.model.TransactionMutableView
import com.aravindh.spendsmart.ui.theme.cyan600
import com.aravindh.spendsmart.ui.theme.red400
import com.aravindh.spendsmart.ui.theme.red900
import com.google.accompanist.flowlayout.FlowCrossAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import java.util.Calendar

@OptIn(ExperimentalMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MonthlyScreen(navController: NavController, viewModel: MonthlyViewModel) {

    var visible by remember {
        mutableStateOf(false)
    }
    var date by remember {
        mutableStateOf("")
    }
    val allTransaction by viewModel.getDataByYearAndMonth(date).observeAsState()
    Log.d("vvv",allTransaction.toString())
    var showDialog by remember {
        mutableStateOf(true)
    }
    val currentMonth = Calendar.getInstance().get(Calendar.MONTH)  // 0-based index
    val currentMonthEnum = Months.values()[currentMonth]
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(androidx.compose.material3.MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.TopStart
    ) {
        Column {
            MonthPicker(
                visible = visible,
                currentMonth = currentMonthEnum,
                currentYear = currentYear,
                confirmButtonCLicked = { month, year ->
                    date = if (month.toString().length == 2) {
                        "$year-$month"
                    } else {
                        "$year-0$month"
                    }
                    visible = false
                },
                cancelClicked = {
                    visible = false
                }
            )
            Row(
                modifier = Modifier.padding(top = 8.dp, end = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                var monthSelectionButtonLabel by remember {
                    mutableStateOf("Select \na month")
                }
                if (date.isNotEmpty()) {
                    monthSelectionButtonLabel = date
                }
                IconButton(
                    onClick = { navController.popBackStack() }
                ) {
                    Icon(
                        Icons.Filled.ArrowBack,
                        "Back",
                        tint = androidx.compose.material3.MaterialTheme.colorScheme.tertiary
                    )
                }
                Text(
                    text = "Monthly Record",
                    fontWeight = FontWeight.Bold,
                    color = androidx.compose.material3.MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .weight(1F),
                    style = MaterialTheme.typography.h6
                )
                OutlinedButton(
                    modifier = Modifier.weight(1F),
                    onClick = { visible = true },
                    border = BorderStroke(2.dp, cyan600)
                ) {
                    Text(text = monthSelectionButtonLabel, color = cyan600)
                }
            }
            val transactionList = mutableListOf<TransactionMutableView>()
            allTransaction?.forEach { transaction ->
                val incomeCategoryImages: ImageVector? = when {
                    (transaction.incomeCategory == IncomeCategory.ALLOWANCE) -> {
                        ImageVector.vectorResource(id = R.drawable.reshot_icon_money_transport_zx3wfh7j68)
                    }

                    (transaction.incomeCategory == IncomeCategory.SALARY) -> {
                        ImageVector.vectorResource(id = R.drawable.reshot_icon_send_money_h9mlb4d7ez)
                    }

                    (transaction.incomeCategory == IncomeCategory.CRYPTO) -> {
                        ImageVector.vectorResource(id = R.drawable.reshot_icon_money_making_zyl8m5jrqs)
                    }

                    (transaction.incomeCategory == IncomeCategory.OTHERS) -> {
                        ImageVector.vectorResource(id = R.drawable.reshot_icon_team_money_8nhdr7vzfb)
                    }

                    (transaction.incomeCategory == IncomeCategory.AWARDS) -> {
                        ImageVector.vectorResource(id = R.drawable.reshot_icon_money_bag_ugdnplyzv7)
                    }

                    (transaction.incomeCategory == IncomeCategory.COUPONS) -> {
                        ImageVector.vectorResource(id = R.drawable.reshot_icon_voucher_4vpc8kwb9g)
                    }

                    (transaction.incomeCategory == IncomeCategory.SALE) -> {
                        ImageVector.vectorResource(id = R.drawable.reshot_icon_sale_cw9v2ujp7f)
                    }

                    (transaction.incomeCategory == IncomeCategory.RENTAL) -> {
                        ImageVector.vectorResource(id = R.drawable.reshot_icon_house_loan_pny3fgzjt4)
                    }

                    (transaction.incomeCategory == IncomeCategory.GIFTS) -> {
                        ImageVector.vectorResource(id = R.drawable.reshot_icon_special_gift_ueh2ny567x)
                    }

                    else -> {
                        null
                    }
                }
                val expenseCategoryImages: ImageVector? = when {
                    (transaction.expenseCategory == ExpenseCategory.BEAUTY) -> {
                        ImageVector.vectorResource(id = R.drawable.reshot_icon_makeup_k7vs8byt5a)
                    }

                    (transaction.expenseCategory == ExpenseCategory.VEHICLE) -> {
                        ImageVector.vectorResource(id = R.drawable.reshot_icon_electric_car_y8tgpu3rb7)
                    }

                    (transaction.expenseCategory == ExpenseCategory.CLOTHING) -> {
                        ImageVector.vectorResource(id = R.drawable.reshot_icon_clothes_lytbq7vgmj)
                    }

                    (transaction.expenseCategory == ExpenseCategory.EDUCATION) -> {
                        ImageVector.vectorResource(id = R.drawable.reshot_icon_education_apps_yawm95r4pl)
                    }

                    (transaction.expenseCategory == ExpenseCategory.HOME) -> {
                        ImageVector.vectorResource(id = R.drawable.reshot_icon_home_78ypw4dhuc)
                    }

                    (transaction.expenseCategory == ExpenseCategory.FOOD) -> {
                        ImageVector.vectorResource(id = R.drawable.reshot_icon_unhealthy_food_g7mubj94z3)
                    }

                    (transaction.expenseCategory == ExpenseCategory.TRANSPORT) -> {
                        ImageVector.vectorResource(id = R.drawable.reshot_icon_train_platform_jv564rcu89)
                    }

                    (transaction.expenseCategory == ExpenseCategory.SHOPPING) -> {
                        ImageVector.vectorResource(id = R.drawable.reshot_icon_woman_shopping_nl38vsbhzr)
                    }

                    (transaction.expenseCategory == ExpenseCategory.ENTERTAINMENT) -> {
                        ImageVector.vectorResource(id = R.drawable.reshot_icon_sports_kl9fswqdhc)
                    }

                    (transaction.expenseCategory == ExpenseCategory.INSURANCE) -> {
                        ImageVector.vectorResource(
                            id = R.drawable.reshot_icon_insurance_slnfkqrbe3
                        )
                    }

                    (transaction.expenseCategory == ExpenseCategory.RECHARGE) -> {
                        ImageVector.vectorResource(id = R.drawable.reshot_icon_rechargeable_battery_q3jzw68n24)
                    }

                    else -> {
                        null
                    }
                }

                transactionList.add(
                    TransactionMutableView(
                        transactionID = transaction.id,
                        transactionType = transaction.transactionType,
                        amount = transaction.amount.toString(),
                        notes = transaction.notes,
                        incomeCategory = transaction.incomeCategory,
                        incomeCategoryImage = incomeCategoryImages,
                        expenseCategory = transaction.expenseCategory,
                        expenseCategoryImage = expenseCategoryImages,
                        paymentMethod = transaction.paymentMethod,
                        createdDate = transaction.createdDate,
                        createdTime = transaction.createdTime
                    )
                )
            }
            if (transactionList.isNotEmpty()) {
                LazyColumn {
                    items(transactionList, key = { it.transactionID!! }) { item ->
                        val dismissState = rememberDismissState(
                            confirmStateChange = { dismissValue ->
                                if (dismissValue == DismissValue.DismissedToStart) {
                                    item.transactionID?.let { viewModel.deleteTransaction(it) }
                                    true
                                } else {
                                    false
                                }
                            }
                        )
                        SwipeToDismiss(
                            state = dismissState,
                            directions = setOf(DismissDirection.EndToStart),
                            background = {
                                val color = when (dismissState.dismissDirection) {
                                    DismissDirection.EndToStart -> red900
                                    else -> Color.Transparent
                                }
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(color)
                                        .padding(horizontal = 20.dp),
                                    contentAlignment = Alignment.CenterEnd
                                ) {

                                    Icon(
                                        Icons.Default.Delete,
                                        contentDescription = "Delete",
                                        tint = Color.White
                                    )
                                }
                            },
                            dismissContent = {
                                IncomeOrExpenseRecyclerViewCard(item = item, onClick = {
                                    val paramValue = item.transactionID
                                    navController.navigate("fabRoute?transactionID=$paramValue")
                                })
                            }
                        )
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 150.dp), contentAlignment = Alignment.TopCenter
                ) {
                    NorRecordFoundCardView()
                }
            }
        }
    }
}


@Composable
fun MonthPicker(
    visible: Boolean,
    currentMonth: Months,
    currentYear: Int,
    confirmButtonCLicked: (Int, Int) -> Unit,
    cancelClicked: () -> Unit
) {

    var month by remember { mutableStateOf(currentMonth) }

    var year by remember {
        mutableStateOf(currentYear)
    }

    val interactionSource = remember {
        MutableInteractionSource()
    }
    if (visible) {
        androidx.compose.material.AlertDialog(
            shape = RoundedCornerShape(10),
            backgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.surface,
            title = {},
            text = {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.KeyboardArrowDown,
                            contentDescription = null,
                            tint = androidx.compose.material3.MaterialTheme.colorScheme.tertiary,
                            modifier = Modifier
                                .size(35.dp)
                                .rotate(90f)
                                .clickable(
                                    indication = null,
                                    interactionSource = interactionSource,
                                    onClick = {
                                        year--
                                    }
                                )
                        )
                        Text(
                            text = year.toString(),
                            modifier = Modifier.padding(20.dp),
                            fontSize = 24.sp,
                            color = androidx.compose.material3.MaterialTheme.colorScheme.tertiary,
                            fontWeight = FontWeight.Bold
                        )
                        Icon(
                            imageVector = Icons.Rounded.KeyboardArrowDown,
                            contentDescription = null,
                            tint = androidx.compose.material3.MaterialTheme.colorScheme.tertiary,
                            modifier = Modifier
                                .size(35.dp)
                                .rotate(-90f)
                                .clickable(
                                    indication = null,
                                    interactionSource = interactionSource,
                                    onClick = {
                                        year++
                                    }
                                )
                        )
                    }
                    Card(
                        modifier = Modifier
                            .padding(top = 30.dp)
                            .fillMaxWidth()
                            .background(androidx.compose.material3.MaterialTheme.colorScheme.surface)
                    ) {
                        FlowRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(androidx.compose.material3.MaterialTheme.colorScheme.background),
                            mainAxisSpacing = 16.dp,
                            crossAxisSpacing = 16.dp,
                            mainAxisAlignment = MainAxisAlignment.Center,
                            crossAxisAlignment = FlowCrossAxisAlignment.Center
                        ) {

                            Months.values().forEach {
                                Box(
                                    modifier = Modifier
                                        .size(60.dp)
                                        .clickable(
                                            indication = null,
                                            interactionSource = interactionSource,
                                            onClick = { month = it })
                                        .background(color = androidx.compose.material3.MaterialTheme.colorScheme.background),
                                    contentAlignment = Alignment.Center
                                ) {
                                    val animationSize by animateDpAsState(
                                        targetValue = if (month == it) 650.dp else 0.dp,
                                        label = "",
                                        animationSpec = tween(
                                            durationMillis = 500,
                                            easing = LinearOutSlowInEasing
                                        )
                                    )
                                    Box(
                                        modifier = Modifier
                                            .size(animationSize)
                                            .background(
                                                color = if (month == it) red400 else Color.White,
                                                shape = CircleShape
                                            )
                                    )
                                    Text(
                                        text = it.value,
                                        color = androidx.compose.material3.MaterialTheme.colorScheme.tertiary
                                    )

                                }
                            }

                        }

                    }
                }
            },
            onDismissRequest = { /*TODO*/ },
            buttons = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 20.dp, bottom = 30.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    androidx.compose.material.OutlinedButton(
                        modifier = Modifier.padding(end = 20.dp),
                        onClick = { cancelClicked() },
                        shape = CircleShape,
                        border = BorderStroke(1.dp, color = Color.White),
                        colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.White)
                    ) {
                        Text(
                            text = "Cancel",
                            color = Color.Black,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    androidx.compose.material.OutlinedButton(
                        modifier = Modifier.padding(end = 20.dp),
                        onClick = {
                            confirmButtonCLicked(
                                Months.values().toList().indexOf(month) + 1, year
                            )
                        },
                        shape = CircleShape,
                        border = BorderStroke(
                            1.dp,
                            color = androidx.compose.material3.MaterialTheme.colorScheme.tertiary
                        ),
                        colors = ButtonDefaults.outlinedButtonColors(backgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.primary)
                    ) {
                        Text(
                            text = "OK",
                            color = androidx.compose.material3.MaterialTheme.colorScheme.tertiary,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        )
    }
}