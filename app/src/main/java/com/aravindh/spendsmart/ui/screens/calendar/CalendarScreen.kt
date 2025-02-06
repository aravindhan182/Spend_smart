package com.aravindh.spendsmart.ui.screens.calendar

import android.app.DatePickerDialog
import android.content.Context
import android.icu.util.Calendar
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.aravindh.spendsmart.ui.theme.white


@Composable
fun DatePickerEnhancedExample(navController: NavController) {
    var selectedDate by remember { mutableStateOf<String?>(null) }
    var openCalendar by remember { mutableStateOf(false) }
    val context = LocalContext.current
    if (openCalendar) {
        DatePicker(context = context, onDateSelected = { date ->
            selectedDate = date
            openCalendar = false
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
            Text(
                text = "You can select date range here",
                fontStyle = FontStyle.Italic,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(1f),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, color = white)
                ) {
                    Column(modifier = Modifier.background(color = MaterialTheme.colorScheme.surfaceVariant)) {
                        Text("From Date", modifier = Modifier.padding(16.dp), color = white)
                    }
                }

                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(1f),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, color = white)
                ) {
                    Column(modifier = Modifier.background(color = MaterialTheme.colorScheme.surfaceVariant)) {
                        Text("To Date", modifier = Modifier.padding(16.dp), color = white)
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        AnimatedVisibility(
            visible = selectedDate != null,
            enter = fadeIn()
        ) {
            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "Selected Date: $selectedDate",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun DatePicker(
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
            val formattedDate = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
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
