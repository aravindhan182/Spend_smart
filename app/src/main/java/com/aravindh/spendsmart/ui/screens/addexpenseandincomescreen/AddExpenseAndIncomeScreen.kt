package com.aravindh.spendsmart.ui.screens.addexpenseandincomescreen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
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
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.aravindh.spendsmart.ui.screens.model.Catergories
import com.aravindh.spendsmart.ui.theme.dark_cyan
import kotlinx.coroutines.launch
import java.util.Calendar


@Composable
fun AddExpenseAndIncomeScreen(navController: NavController) {
    var selectedText by remember { mutableStateOf("INCOME") }
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
                    selectedText = dropDownWithTextField()
                    Spacer(modifier = Modifier.padding(8.dp))
                    CategoryCardView(selectedText)
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
fun dropDownWithTextField(): String {
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
    return selectedText
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryCardView(selectedText: String) {
    var selectedCategory by remember { mutableStateOf("Allowance") }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedCategoryIndex by remember { mutableStateOf(-1) }
    val isIncome = selectedText == "INCOME"
    // Main Card
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color.Black),
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                scope.launch { showBottomSheet = true }
            }
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
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
                        text = selectedCategory,
                        modifier = Modifier.padding(8.dp),
                        textAlign = TextAlign.Start,
                    )
                }
            }
        }
    }

    // Bottom Sheet
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState
        ) {

            val expenseCategories = listOf(
                "Beauty",
                "Vehicle",
                "Clothing",
                "Education",
                "Home",
                "Food",
                "Transport",
                "Shopping",
                "Entertainment",
                "Insurance",
                "Recharge"
            )
            val incomeCategories = listOf(
                "Allowance",
                "Salary",
                "Crypto",
                "Others",
                "Awards",
                "Coupons",
                "Sale",
                "Rental",
                "Gifts"
            )

            val expenseCategoriesIcons = listOf(
                ImageVector.vectorResource(id = R.drawable.reshot_icon_makeup_k7vs8byt5a),
                ImageVector.vectorResource(id = R.drawable.reshot_icon_electric_car_y8tgpu3rb7),
                ImageVector.vectorResource(id = R.drawable.reshot_icon_clothes_lytbq7vgmj),
                ImageVector.vectorResource(id = R.drawable.reshot_icon_education_apps_yawm95r4pl),
                ImageVector.vectorResource(id = R.drawable.reshot_icon_home_78ypw4dhuc),
                ImageVector.vectorResource(id = R.drawable.reshot_icon_unhealthy_food_g7mubj94z3),
                ImageVector.vectorResource(id = R.drawable.reshot_icon_train_platform_jv564rcu89),
                ImageVector.vectorResource(id = R.drawable.reshot_icon_woman_shopping_nl38vsbhzr),
                ImageVector.vectorResource(id = R.drawable.reshot_icon_sports_kl9fswqdhc),
                ImageVector.vectorResource(
                    id = R.drawable.reshot_icon_insurance_slnfkqrbe3
                ),
                ImageVector.vectorResource(id = R.drawable.reshot_icon_rechargeable_battery_q3jzw68n24)
            )
            val incomeCategoriesIcons = listOf(
                ImageVector.vectorResource(id = R.drawable.reshot_icon_money_transport_zx3wfh7j68),
                ImageVector.vectorResource(id = R.drawable.reshot_icon_send_money_h9mlb4d7ez),
                ImageVector.vectorResource(id = R.drawable.reshot_icon_money_making_zyl8m5jrqs),
                ImageVector.vectorResource(id = R.drawable.reshot_icon_team_money_8nhdr7vzfb),
                ImageVector.vectorResource(id = R.drawable.reshot_icon_money_bag_ugdnplyzv7),
                ImageVector.vectorResource(id = R.drawable.reshot_icon_voucher_4vpc8kwb9g),
                ImageVector.vectorResource(id = R.drawable.reshot_icon_sale_cw9v2ujp7f),
                ImageVector.vectorResource(id = R.drawable.reshot_icon_house_loan_pny3fgzjt4),
                ImageVector.vectorResource(id = R.drawable.reshot_icon_special_gift_ueh2ny567x)
            )
            val categories = if (!isIncome) (Catergories(
                expenseCategories,
                expenseCategoriesIcons
            )) else (Catergories(incomeCategories, incomeCategoriesIcons))
            LazyVerticalGrid(
                columns = GridCells.Fixed(2), // 2 columns
                contentPadding = PaddingValues(16.dp),
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categories.catergoryName.size) { index ->
                    GridItem(
                        item = categories.catergoryName[index],
                        imageVector = categories.categoryImage[index],
                        isSelected = selectedCategoryIndex == index,
                        onClick = {
                            selectedCategoryIndex = index
                            selectedCategory = incomeCategories[index]
                            showBottomSheet = false
                        }
                    )
                }

            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun PaymentMethodCardView() {
    var selectedPaymentMethod by remember { mutableStateOf("Credit Card") }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedCategoryIndex by remember { mutableStateOf(-1) }
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        border = BorderStroke(1.dp, Color.Black),
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                scope.launch { showBottomSheet = true }
            }
    ) {
        Column {

            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = {
                        showBottomSheet = false
                    },
                    sheetState = sheetState
                ) {
                    val paymentMethods =
                        listOf("Credit Card", "Debit Card", "UPI", "Net Banking")
                    val paymentMethodsIcons = listOf(
                        ImageVector.vectorResource(id = R.drawable.reshot_icon_credit_cards_fq5dacrukz),
                        ImageVector.vectorResource(id = R.drawable.reshot_icon_credit_card_ztwh96snqm),
                        ImageVector.vectorResource(id = R.drawable.reshot_icon_mobile_mz2dkxvsbw),
                        ImageVector.vectorResource(id = R.drawable.reshot_icon_computer_dollar_nfvhye9jp4)
                    )
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2), // 2 columns
                        contentPadding = PaddingValues(16.dp),
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(paymentMethods.size) { index ->
                            GridItem(
                                item = paymentMethods[index],
                                imageVector = paymentMethodsIcons[index],
                                isSelected = selectedCategoryIndex == index,
                                onClick = {
                                    selectedCategoryIndex = index
                                    selectedPaymentMethod = paymentMethods[index]
                                    showBottomSheet = false
                                }
                            )
                        }
                    }


                }
            }
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
                        text = selectedPaymentMethod,
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
    var selectedDates by remember { mutableStateOf("No Date Selected") }
    var selectedTimes by remember { mutableStateOf("No Time Selected") }

    val timePickerState = rememberTimePickerState()
    var showTimePicker by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .wrapContentSize()
            .padding(start = 16.dp, bottom = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        Text(text = selectedDates, modifier = Modifier.padding(bottom = 16.dp))

        Button(
            onClick = {
                showDatePicker = true //changing the visibility state
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(text = "Date Picker")
        }

        Divider(modifier = Modifier.padding(vertical = 24.dp))

        Text(text = selectedTimes, modifier = Modifier.padding(bottom = 16.dp))

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
                        selectedDates = selectedDate.toString()
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
                        selectedTimes = selectedDate.time.toString()
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryBottomSheetDialog(showBottomSheet: MutableState<Boolean>) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(showBottomSheet) }
    Column {

        if (showBottomSheet.value) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet.value = false
                },
                sheetState = sheetState
            ) {
                // Sheet content
                Button(onClick = {
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            showBottomSheet.value = false
                        }
                    }
                }) {
                    Text("Hide bottom sheet")
                }
            }
        }
    }
}


@Composable
fun GridItem(
    item: String,
    imageVector: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) dark_cyan else Color.LightGray
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    imageVector = imageVector,
                    contentDescription = "category image",
                    modifier = Modifier
                        .width(100.dp)
                        .height(100.dp)
                        .padding(bottom = 8.dp)
                )
                Text(
                    text = item,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}