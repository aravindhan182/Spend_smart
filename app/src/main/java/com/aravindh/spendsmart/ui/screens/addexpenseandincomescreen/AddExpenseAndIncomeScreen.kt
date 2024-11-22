package com.aravindh.spendsmart.ui.screens.addexpenseandincomescreen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.aravindh.spendsmart.R
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseAndIncomeScreen(navController: NavController) {
    Scaffold(
        floatingActionButton = { MyExtendedFab() },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .align(Alignment.TopStart)
                ) {
                    Icon(Icons.Filled.ArrowBack, "Back")
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "INCOME OR EXPENSE", fontWeight = FontWeight.Bold, fontSize = 24.sp)
                    Spacer(modifier = Modifier.padding(8.dp))
                    DropDownWithTextField()
                    Spacer(modifier = Modifier.padding(8.dp))
                    CategoryCardView()
                    Spacer(modifier = Modifier.padding(8.dp))
                    PaymentMethodCardView()
                    Spacer(modifier = Modifier.padding(8.dp))
                    OutlinedTextField(
                        value = "", onValueChange = {},
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        label = {
                            androidx.compose.material.Text(
                                text = "Notes..."
                            )
                        },
                    )
                    Spacer(modifier = Modifier.padding(16.dp))
                    DateTimePickerComponent(LocalContext.current)
                    Spacer(modifier = Modifier.padding(16.dp))
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownWithTextField() {
    val list = listOf("INCOME", "EXPENSE")
    var isExpanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(list[0]) }
    var textFieldValue by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            ExposedDropdownMenuBox(
                expanded = isExpanded,
                onExpandedChange = { isExpanded = !isExpanded },
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 8.dp)
            ) {
                OutlinedTextField(
                    modifier = Modifier.menuAnchor(),
                    value = selectedText,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                    }
                )
                ExposedDropdownMenu(
                    expanded = isExpanded,
                    onDismissRequest = { isExpanded = false }) {
                    list.forEachIndexed { index, s ->
                        DropdownMenuItem(
                            text = { Text(text = s) },
                            onClick = {
                                selectedText = list[index]
                                isExpanded = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                        )
                    }
                }
            }
            OutlinedTextField(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp),
                value = textFieldValue,
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.dollar_minimalistic_svgrepo_com),
                        contentDescription = "Add"
                    )
                },
                onValueChange = { textFieldValue = it },
                label = { Text("Amount") }
            )
        }
    }
}

@Composable
fun CategoryCardView() {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        border = BorderStroke(1.dp, Color.Black),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.mobile_payment_svgrepo_com),
                    contentDescription = "category image",
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp)
                        .padding(bottom = 8.dp)
                )
                Column {
                    Text(
                        text = "Category",
                        modifier = Modifier.padding(8.dp),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start,
                    )
                    Text(
                        text = "Allowance",
                        modifier = Modifier.padding(8.dp),
                        textAlign = TextAlign.Start,
                    )
                }
            }
        }
    }
}


@Composable
fun PaymentMethodCardView() {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        border = BorderStroke(1.dp, Color.Black),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.mobile_payment_dollar_svgrepo_com),
                    contentDescription = "category image",
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp)
                        .padding(bottom = 8.dp)
                )
                Column {
                    Text(
                        text = "Payment Methods",
                        modifier = Modifier.padding(8.dp),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start,
                    )
                    Text(
                        text = "Credit Card",
                        modifier = Modifier.padding(8.dp),
                        textAlign = TextAlign.Start,
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTimePickerComponent(context: Context) {
    val datePickerState = rememberDatePickerState()
    var showDatePicker by remember { mutableStateOf(false) }

    val timePickerState = rememberTimePickerState()
    var showTimePicker by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .wrapContentSize()
            .padding(start = 16.dp, bottom = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        Text(text = "No Date Selected", modifier = Modifier.padding(bottom = 16.dp))

        Button(
            onClick = {
                showDatePicker = true //changing the visibility state
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(text = "Date Picker")
        }

        Divider(modifier = Modifier.padding(vertical = 24.dp))

        Text(text = "No Time Selected", modifier = Modifier.padding(bottom = 16.dp))

        Button(
            onClick = {
                showTimePicker = true //changing the visibility state
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(text = "Time Picker")
        }

    }

// date picker component
    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { /*TODO*/ },
            confirmButton = {
                TextButton(
                    onClick = {
                        val selectedDate = Calendar.getInstance().apply {
                            timeInMillis = datePickerState.selectedDateMillis ?: 0L
                        }
                        Toast.makeText(
                            context,
                            "Selected date ${selectedDate.time} saved",
                            Toast.LENGTH_SHORT
                        ).show()
                        showDatePicker = false
                    }
                ) { Text("OK") }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDatePicker = false
                    }
                ) { Text("Cancel") }
            }
        )
        {
            DatePicker(state = datePickerState)
        }
    }

// time picker component
    if (showTimePicker) {
        TimePickerDialog(
            onDismissRequest = { /*TODO*/ },
            confirmButton = {
                TextButton(
                    onClick = {
                        val selectedDate = Calendar.getInstance().apply {
                            this.time
                        }
                        Toast.makeText(
                            context,
                            "Selected time ${selectedDate.time} saved",
                            Toast.LENGTH_SHORT
                        ).show()
                        showTimePicker = false
                    }
                ) { Text("OK") }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showTimePicker = false
                    }
                ) { Text("Cancel") }
            }
        )
        {
            TimePicker(state = timePickerState)
        }
    }

}

@Composable
fun TimePickerDialog(
    title: String = "Select Time",
    onDismissRequest: () -> Unit,
    confirmButton: @Composable (() -> Unit),
    dismissButton: @Composable (() -> Unit)? = null,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = 6.dp,
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .height(IntrinsicSize.Min)
                .background(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = containerColor
                ),
            color = containerColor
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    text = title,
                    style = MaterialTheme.typography.labelMedium
                )
                content()
                Row(
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    dismissButton?.invoke()
                    confirmButton()
                }
            }
        }
    }
}

@Composable
fun MyExtendedFab() {
    Box {
        ExtendedFloatingActionButton(
            onClick = { /* Handle FAB click */ },
            text = { Text("SAVE", fontSize = 16.sp) },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.save_svgrepo_com),
                    contentDescription = "Add"
                )
            },
            modifier = Modifier
                .align(Alignment.BottomEnd),
            shape = RoundedCornerShape(12.dp),
            containerColor = Color(0xFFF42121),
            contentColor = Color(0xFFFFFFFF)
        )
    }
}

@Composable
fun CategoryBottomSheetDialog() {

}