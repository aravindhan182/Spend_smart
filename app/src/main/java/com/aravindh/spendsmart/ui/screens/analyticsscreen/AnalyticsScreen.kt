package com.aravindh.spendsmart.ui.screens.analyticsscreen

import android.graphics.Typeface
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.DropdownMenu
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.aravindh.spendsmart.R
import com.aravindh.spendsmart.ui.theme.cyan600
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

@Composable
fun AnalyticsScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.TopStart
    ) {
        Column {
            Row(modifier = Modifier.padding(top = 8.dp)) {
                IconButton(
                    onClick = { navController.popBackStack() }
                ) {
                    Icon(Icons.Filled.ArrowBack, "Back", tint = MaterialTheme.colorScheme.tertiary)
                }
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(
                        text = "Analytics",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                }
                SimpleDropdownMenu()
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
            CreatePieChart()
        }
    }
}

@Composable
fun CreatePieChart() {
    val getPieChartData = listOf(
        PieChartView("Beauty", 34.68F),
        PieChartView("Vehicle", 16.60F),
        PieChartView("Clothing", 16.15F),
        PieChartView("Education", 15.62F),
        PieChartView("Home", 16.95F),
    )
    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .padding(18.dp)
                    .size(320.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Crossfade(targetState = getPieChartData, label = "") { chartValue ->

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

fun updatePieChartWithData(
    chart: PieChart,
    data: List<PieChartView>
) {
    val entries = ArrayList<PieEntry>()
    for (i in data.indices) {
        val item = data[i]
        entries.add(PieEntry(item.pieValue ?: 0.toFloat(), item.pieDataName ?: ""))
    }
    val ds = PieDataSet(entries, "")
    ds.colors = arrayListOf(
        Color(0xFF13af15).toArgb(),
        Color(0xFF1326AF).toArgb(),
        Color(0xFFf20848).toArgb(),
        Color(0xFFF2e708).toArgb(),
        Color(0xFFe0740F).toArgb(),
    )
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

@Composable
fun SimpleDropdownMenu() {
    val options = listOf("Today", "Date Range")
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(options[0]) }
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 4.dp, end = 8.dp, top = 4.dp, bottom = 4.dp)
    ) {
        OutlinedButton(
            onClick = { expanded = true },
            modifier = Modifier.fillMaxWidth(),
            border = BorderStroke(2.dp, cyan600)
        ) {
            Text(selectedOption, color = cyan600)
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = "Dropdown",
                modifier = Modifier.padding(start = 8.dp),
                tint = cyan600
            )
        }

        DropdownMenu(
            modifier = Modifier.background(MaterialTheme.colorScheme.inverseSurface),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option, color = MaterialTheme.colorScheme.tertiary) },
                    onClick = {
                        selectedOption = option
                        expanded = false
                    }
                )
            }
        }
    }
}
