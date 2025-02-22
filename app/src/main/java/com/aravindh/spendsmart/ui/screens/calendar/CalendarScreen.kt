package com.aravindh.spendsmart.ui.screens.calendar

import android.app.DatePickerDialog
import android.content.Context
import android.icu.util.Calendar
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.aravindh.spendsmart.R
import com.aravindh.spendsmart.data.expense.ExpenseCategory
import com.aravindh.spendsmart.data.expense.IncomeCategory
import com.aravindh.spendsmart.data.expense.Transaction
import com.aravindh.spendsmart.ui.screens.daily.IncomeOrExpenseRecyclerViewCard
import com.aravindh.spendsmart.ui.screens.daily.NorRecordFoundCardView
import com.aravindh.spendsmart.ui.screens.model.TransactionMutableView
import com.aravindh.spendsmart.ui.theme.red900
import com.aravindh.spendsmart.ui.theme.white
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale


@OptIn(ExperimentalMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarScreen(navController: NavController, viewModel: CalendarViewModel) {
    var openFromCalendar by remember { mutableStateOf(false) }
    var openToCalendar by remember { mutableStateOf(false) }
    var dataFetched by remember { mutableStateOf(false) }
val dateRange by viewModel.dateRange.observeAsState(
    MutableDateRange()
)
    var allTransaction by remember { mutableStateOf<List<Transaction>?>(emptyList()) }
    val formatter = DateTimeFormatter.ISO_LOCAL_DATE
    val localFromDate: LocalDate? = dateRange.fromDate?.let {
        LocalDate.parse(it, formatter)
    }
    val localToDate: LocalDate? = dateRange.toDate?.let {
        LocalDate.parse(it, formatter)
    }
    LaunchedEffect(dataFetched, localFromDate) {
        if (dataFetched && localFromDate != null) {
            viewModel.getDataByDateRange(localFromDate, localToDate?:LocalDate.now())
                .observeForever { transactions ->
                    allTransaction = transactions
                }
            dataFetched = false
        } else {

        }
    }

    val context = LocalContext.current
    if (openFromCalendar) {
        FromDatePicker(context = context, onDateSelected = { date ->
            dateRange.fromDate = date
            openFromCalendar = false
        })
    }
    if (openToCalendar) {
        ToDatePicker(context = context, onDateSelected = { date ->
            dateRange.toDate = date
            openToCalendar = false
        })
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onSurface),
            elevation = CardDefaults.cardElevation(4.dp),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, color = white)
        ) {
            Column {
                Text(
                    text = "You can select date range here",
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp)
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .weight(1f)
                            .clickable {
                                openFromCalendar = true
                            },
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, color = white)
                    ) {
                        Column(modifier = Modifier.background(color = MaterialTheme.colorScheme.surfaceVariant)) {
                            Text(
                                dateRange.fromDate ?: "From Date",
                                modifier = Modifier.padding(16.dp),
                                color = white
                            )
                        }
                    }

                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .weight(1f)
                            .clickable {
                                openToCalendar = true
                            },
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, color = white)
                    ) {
                        Column(modifier = Modifier.background(color = MaterialTheme.colorScheme.surfaceVariant)) {
                            Text(
                                dateRange.toDate ?: "To Date",
                                modifier = Modifier.padding(16.dp),
                                color = white
                            )
                        }
                    }
                }
                Button(
                    onClick = { dataFetched = true },
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(end = 16.dp, top = 8.dp, bottom = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text(
                        text = "GET",
                        color = MaterialTheme.colorScheme.tertiary
                    )

                }
            }
        }
        val transactionList = mutableListOf<TransactionMutableView>()
        allTransaction?.forEach { transaction ->
            Log.d("vvvv", transaction.toString())
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

@Composable
fun FromDatePicker(
    context: Context,
    onDateSelected: (String) -> Unit
) {
    val calendar = Calendar.getInstance()

    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDayOfMonth ->
            val formattedDate = String.format(
                Locale.UK,
                "%04d-%02d-%02d",
                selectedYear,
                selectedMonth + 1,
                selectedDayOfMonth
            )
            onDateSelected(formattedDate)
        },
        year,
        month,
        day
    )

    LaunchedEffect(Unit) {
        datePickerDialog.show()
    }
}

@Composable
fun ToDatePicker(
    context: Context,
    onDateSelected: (String) -> Unit
) {
    val calendar = Calendar.getInstance()

    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDayOfMonth ->
            val formattedDate = String.format(
                Locale.UK,
                "%04d-%02d-%02d",
                selectedYear,
                selectedMonth + 1,
                selectedDayOfMonth
            )
            onDateSelected(formattedDate)
        },
        year,
        month,
        day
    )

    LaunchedEffect(Unit) {
        datePickerDialog.show()
    }
}
