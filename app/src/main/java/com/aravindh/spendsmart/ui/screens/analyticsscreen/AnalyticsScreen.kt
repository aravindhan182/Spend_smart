package com.aravindh.spendsmart.ui.screens.analyticsscreen

import android.graphics.Typeface
import android.os.Build
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.compose.animation.Crossfade
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.aravindh.spendsmart.R
import com.aravindh.spendsmart.data.expense.Transaction
import com.aravindh.spendsmart.data.expense.TransactionType
import com.aravindh.spendsmart.ui.screens.calendar.FromDatePicker
import com.aravindh.spendsmart.ui.screens.calendar.MutableDateRange
import com.aravindh.spendsmart.ui.screens.calendar.ToDatePicker
import com.aravindh.spendsmart.ui.screens.daily.NorRecordFoundCardView
import com.aravindh.spendsmart.ui.theme.white
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AnalyticsScreen(navController: NavController, viewModel: AnalyticViewModel) {
    val sheetState = rememberModalBottomSheetState()
    var allTransaction by remember { mutableStateOf<List<Transaction>?>(emptyList()) }
    var showBottomSheet by remember { mutableStateOf(false) }
    Scaffold { contentPadding ->
        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState
            ) {
                DateRangeBottomSheet(viewModel = viewModel, onDismiss = { showBottomSheet = it }) {
                    allTransaction = it
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.TopStart
        ) {
            Column {
                Row(modifier = Modifier.padding(top = 8.dp)) {
                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            "Back",
                            tint = MaterialTheme.colorScheme.tertiary
                        )
                    }
                    Column(modifier = Modifier.padding(8.dp)) {
                        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                            Text(
                                modifier = Modifier.weight(1F),
                                text = "Analytics",
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                color = MaterialTheme.colorScheme.tertiary
                            )
                            Button(
                                modifier = Modifier.weight(1F),
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                                onClick = { showBottomSheet = true }) {
                                Text(text = "Select Date", fontWeight = FontWeight.SemiBold)
                            }
                        }
                    }

                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    elevation = CardDefaults.cardElevation(5.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp, top = 8.dp, end = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "INCOME",
                                modifier = Modifier.padding(8.dp),
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "EXPENSE",
                                modifier = Modifier.padding(8.dp),
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "50,000",
                                modifier = Modifier.padding(8.dp),
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF13af15)
                            )
                            Text(
                                text = "60,000",
                                modifier = Modifier.padding(8.dp),
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFFD12410)
                            )
                        }
                    }
                }
                Text(
                    text = "Expense Overview",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.padding(start = 8.dp)
                )
                CreatePieChart(allTransaction)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateRangeBottomSheet(
    viewModel: AnalyticViewModel,
    onDismiss: (Boolean) -> Unit,
    expenses: (List<Transaction>) -> Unit
) {
    var openFromCalendar by remember { mutableStateOf(false) }
    var openToCalendar by remember { mutableStateOf(false) }
    var dataFetched by remember { mutableStateOf(false) }
    val dateRange by viewModel.dateRange.observeAsState(
        MutableDateRange()
    )
    val formatter = DateTimeFormatter.ISO_LOCAL_DATE
    val localFromDate: LocalDate? = dateRange.fromDate?.let {
        LocalDate.parse(it, formatter)
    }
    val localToDate: LocalDate? = dateRange.toDate?.let {
        LocalDate.parse(it, formatter)
    }
    LaunchedEffect(dataFetched) {
        if (dataFetched && localFromDate != null) {
            viewModel.getDataByDateRange(
                TransactionType.EXPENSE,
                localFromDate,
                localToDate ?: LocalDate.now()
            )
                .observeForever { transactions ->
                    expenses(transactions)
                    onDismiss(false)
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
            .wrapContentHeight()
            .fillMaxWidth()
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
                    onClick = {
                        dataFetched = true
                    },
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(end = 16.dp, top = 8.dp, bottom = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text(
                        text = "GET",
                        color = white,
                        fontWeight = FontWeight.SemiBold
                    )

                }
            }
        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CreatePieChart(expenses: List<Transaction>?) {

    val categoryWiseExpense = expenses
        ?.groupBy { it.expenseCategory }
        ?.mapValues { entry -> entry.value.sumOf { it.amount } }

    val totalExpense = categoryWiseExpense?.values?.sum() ?: 0.0

    val analyticsList = categoryWiseExpense?.map { (category, amount) ->
        val percentage = if (totalExpense > 0) ((amount / totalExpense) * 100).toFloat() else 0f
        ExpenseAnalytics(
            category = category!!,
            percentage = String.format(Locale.UK, "%.2f", percentage).toFloat()
        )
    }
    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (analyticsList.isNullOrEmpty()) {
                NorRecordFoundCardView()
            } else {

                Column(
                    modifier = Modifier
                        .padding(18.dp)
                        .size(320.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Crossfade(targetState = analyticsList, label = "") { chartValue ->

                        AndroidView(factory = { context ->
                            PieChart(context).apply {
                                layoutParams = LinearLayout.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.MATCH_PARENT
                                )
                                this.description.isEnabled = false
                                this.isDrawHoleEnabled = false
                                this.legend.isEnabled = true
                                this.legend.textSize = 14F
                                this.legend.textColor = R.color.white
                                this.legend.horizontalAlignment =
                                    Legend.LegendHorizontalAlignment.CENTER
                                this.setEntryLabelColor(R.color.brown)
                            }
                        }, modifier = Modifier
                            .wrapContentSize()
                            .padding(5.dp), update = {
                            updatePieChartWithData(it, chartValue)
                        })
                    }
                }
            }
        }
    }
}

fun updatePieChartWithData(
    chart: PieChart,
    data: List<ExpenseAnalytics>
) {
    val entries = ArrayList<PieEntry>()
    for (i in data.indices) {
        val item = data[i]
        entries.add(PieEntry(item.percentage, item.category.value))
    }
    val ds = PieDataSet(entries, "")
    ds.colors = List(data.count()) {
        Color(
            red = Random.nextFloat(),
            green = Random.nextFloat(),
            blue = Random.nextFloat(),
            alpha = 1f
        ).toArgb()
    }

    ds.yValuePosition = PieDataSet.ValuePosition.INSIDE_SLICE
    ds.xValuePosition = PieDataSet.ValuePosition.INSIDE_SLICE
    ds.sliceSpace = 2f

    ds.valueTextColor = R.color.white
    ds.valueTextSize = 18f
    ds.valueTypeface = Typeface.DEFAULT
    val d = PieData(ds)
    chart.data = d
    chart.invalidate()
}



