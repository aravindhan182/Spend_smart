package com.aravindh.spendsmart.ui.screens.transactionscreen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
import com.aravindh.spendsmart.data.expense.ExpenseCategory
import com.aravindh.spendsmart.data.expense.IncomeCategory
import com.aravindh.spendsmart.data.expense.PaymentMethod
import com.aravindh.spendsmart.data.expense.TransactionType
import com.aravindh.spendsmart.ui.screens.model.ExpenseCategoryView
import com.aravindh.spendsmart.ui.screens.model.IncomeCategoryView
import com.aravindh.spendsmart.ui.screens.model.TransactionMutableView
import com.aravindh.spendsmart.ui.theme.cyan800
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TransactionScreen(
    navController: NavController,
    viewModel: TransactionViewModel,
    transactionID: String?
) {
    val successfullySaved by viewModel.saved.observeAsState(false)
    val transaction by viewModel.transactionData.observeAsState(
        TransactionMutableView(
            transactionType = TransactionType.INCOME,
            amount = "",
            notes = ""
        )
    )

    LaunchedEffect(successfullySaved) {
        if (successfullySaved) {
            navController.popBackStack()
            viewModel.resetSavedState()
        }
    }
    if (!transactionID.isNullOrEmpty()) {
        viewModel.getTransactionData(transactionID)
    }
    Scaffold(
        floatingActionButton = { MyExtendedFab(viewModel, transactionID = transactionID) },
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
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        "Back",
                        tint = MaterialTheme.colorScheme.tertiary
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = transaction.transactionType.value,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = MaterialTheme.colorScheme.tertiary
                    )

                    Spacer(modifier = Modifier.padding(8.dp))
                    viewModel.updateTransactionType(dropDownWithTextField(viewModel))
                    Spacer(modifier = Modifier.padding(8.dp))
                    CategoryCardView(transaction.transactionType.value, viewModel)
                    Spacer(modifier = Modifier.padding(8.dp))
                    if (transaction.transactionType == TransactionType.EXPENSE) {
                        PaymentMethodCardView(viewModel)
                        Spacer(modifier = Modifier.padding(4.dp))
                    }
                    OutlinedTextField(
                        value = transaction.notes,
                        onValueChange = {
                            viewModel.updateNotes(it)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        label = {
                            Text(
                                text = "Notes..."
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.onTertiaryContainer,
                            unfocusedContainerColor = MaterialTheme.colorScheme.onTertiaryContainer,
                            focusedIndicatorColor = MaterialTheme.colorScheme.tertiary,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.tertiary,
                            focusedTextColor = MaterialTheme.colorScheme.tertiary,
                            unfocusedLabelColor = MaterialTheme.colorScheme.tertiary,
                            focusedLabelColor = MaterialTheme.colorScheme.primary,
                            unfocusedTextColor = MaterialTheme.colorScheme.tertiary,
                            focusedTrailingIconColor = MaterialTheme.colorScheme.tertiary,
                            unfocusedTrailingIconColor = MaterialTheme.colorScheme.tertiary
                        ),
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    DateTimePickerComponent(viewModel)
                    Spacer(modifier = Modifier.padding(8.dp))
                }
            }
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun dropDownWithTextField(viewModel: TransactionViewModel): TransactionType {
    val transactionTypes = TransactionType.values()
    var isExpanded by remember { mutableStateOf(false) }
    var selectedType by remember { mutableStateOf(TransactionType.INCOME) }
    val transaction by viewModel.transactionData.observeAsState(
        TransactionMutableView(
            transactionType = TransactionType.INCOME,
            amount = "",
            notes = ""
        )
    )
    val transactionError by viewModel.transactionDataError.observeAsState()

    Column(
        modifier = Modifier.fillMaxWidth(),
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
                    value = selectedType.value, // Display the value from enum
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.onTertiaryContainer,
                        unfocusedContainerColor = MaterialTheme.colorScheme.onTertiaryContainer,
                        focusedIndicatorColor = MaterialTheme.colorScheme.tertiary,
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.tertiary,
                        focusedTextColor = MaterialTheme.colorScheme.tertiary,
                        unfocusedTextColor = MaterialTheme.colorScheme.tertiary,
                        focusedTrailingIconColor = MaterialTheme.colorScheme.tertiary,
                        unfocusedTrailingIconColor = MaterialTheme.colorScheme.tertiary
                    )
                )
                ExposedDropdownMenu(
                    modifier = Modifier.background(MaterialTheme.colorScheme.inverseSurface),
                    expanded = isExpanded,
                    onDismissRequest = { isExpanded = false }) {
                    transactionTypes.forEach { type ->
                        DropdownMenuItem(
                            text = { Text(text = type.value) },
                            onClick = {
                                selectedType = type
                                isExpanded = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                            colors = MenuDefaults.itemColors(textColor = MaterialTheme.colorScheme.tertiary)
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentHeight()
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .padding(start = 8.dp),
                    value = transaction.amount,
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.dollar_minimalistic_svgrepo_com),
                            contentDescription = "Add",
                            tint = MaterialTheme.colorScheme.tertiary
                        )
                    },
                    onValueChange = { viewModel.updateAmount(it) },
                    label = { Text("Amount", color = MaterialTheme.colorScheme.tertiary) },
                    colors = TextFieldDefaults.colors(
                        errorTextColor = MaterialTheme.colorScheme.tertiary,
                        focusedContainerColor = MaterialTheme.colorScheme.onTertiaryContainer,
                        errorContainerColor = MaterialTheme.colorScheme.onTertiaryContainer,
                        unfocusedContainerColor = MaterialTheme.colorScheme.onTertiaryContainer,
                        focusedIndicatorColor = MaterialTheme.colorScheme.tertiary,
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.tertiary,
                        focusedTextColor = MaterialTheme.colorScheme.tertiary,
                        unfocusedTextColor = MaterialTheme.colorScheme.tertiary,
                        focusedTrailingIconColor = MaterialTheme.colorScheme.tertiary,
                        unfocusedTrailingIconColor = MaterialTheme.colorScheme.tertiary
                    ),
                    isError = transactionError?.amountError != null
                )
                if (transactionError?.amountError != null) {
                    Text(
                        text = transactionError?.amountError!!,
                        color = Color.Red,
                        style = androidx.compose.material.MaterialTheme.typography.body2,
                        modifier = Modifier.padding(start = 4.dp, top = 4.dp, end = 4.dp)
                    )
                }
            }
        }
    }
    return selectedType
}


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryCardView(selectedText: String, viewModel: TransactionViewModel) {
    val isIncome = selectedText == "INCOME"
    val expenseCategories = ExpenseCategory.values()
    val incomeCategories = IncomeCategory.values()
    var selectedExpenseCategory by remember { mutableStateOf(ExpenseCategory.FOOD) }
    var selectedIncomeCategory by remember { mutableStateOf(IncomeCategory.SALE) }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedIncomeCategoryIndex by remember { mutableIntStateOf(-1) }
    var selectedExpenseCategoryIndex by remember { mutableIntStateOf(-1) }
    Card(
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                scope.launch { showBottomSheet = true }
            },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
        )
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
                        text = if (isIncome) selectedIncomeCategory.value else selectedExpenseCategory.value,
                        modifier = Modifier.padding(8.dp),
                        textAlign = TextAlign.Start,
                    )
                }
            }
        }
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState
        ) {


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
            val exy = ExpenseCategoryView(expenseCategories.toList(), expenseCategoriesIcons)
            val ixy = IncomeCategoryView(incomeCategories.toList(), incomeCategoriesIcons)

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (!isIncome) {
                    items(exy.catergoryName.size) { index ->
                        GridItem(
                            item = exy.catergoryName[index].toString(),
                            imageVector = exy.categoryImage[index],
                            isSelected = selectedExpenseCategoryIndex == index,
                            onClick = {
                                selectedExpenseCategoryIndex = index
                                selectedExpenseCategory = expenseCategories[index]
                                showBottomSheet = false
                                viewModel.updateExpenseCategory(selectedExpenseCategory)
                            }
                        )
                    }
                } else {
                    items(ixy.catergoryName.size) { index ->
                        GridItem(
                            item = ixy.catergoryName[index].toString(),
                            imageVector = ixy.categoryImage[index],
                            isSelected = selectedIncomeCategoryIndex == index,
                            onClick = {
                                selectedIncomeCategoryIndex = index
                                selectedIncomeCategory = incomeCategories[index]
                                showBottomSheet = false
                                viewModel.updateIncomeCategory(selectedIncomeCategory)
                            }
                        )
                    }
                }


            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentMethodCardView(viewModel: TransactionViewModel) {
    var selectedPaymentMethod by remember { mutableStateOf(PaymentMethod.CASH) }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedCategoryIndex by remember { mutableIntStateOf(-1) }
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
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
                    val paymentMethods = PaymentMethod.values().toList()
                    val paymentMethodsIcons = listOf(
                        ImageVector.vectorResource(id = R.drawable.reshot_icon_credit_cards_fq5dacrukz),
                        ImageVector.vectorResource(id = R.drawable.reshot_icon_credit_card_ztwh96snqm),
                        ImageVector.vectorResource(id = R.drawable.reshot_icon_mobile_mz2dkxvsbw),
                        ImageVector.vectorResource(id = R.drawable.reshot_icon_computer_dollar_nfvhye9jp4)
                    )
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(16.dp),
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(paymentMethods.size) { index ->
                            GridItem(
                                item = paymentMethods[index].value,
                                imageVector = paymentMethodsIcons[index],
                                isSelected = selectedCategoryIndex == index,
                                onClick = {
                                    selectedCategoryIndex = index
                                    selectedPaymentMethod = paymentMethods[index]
                                    showBottomSheet = false
                                    viewModel.updatePaymentMethod(selectedPaymentMethod)
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
                        text = selectedPaymentMethod.value,
                        modifier = Modifier.padding(8.dp),
                        textAlign = TextAlign.Start,
                    )
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTimePickerComponent(viewModel: TransactionViewModel) {
    val transaction by viewModel.transactionData.observeAsState(
        TransactionMutableView(
            transactionType = TransactionType.INCOME,
            amount = "",
            notes = ""
        )
    )
    val datePickerState = rememberDatePickerState()
    var showDatePicker by remember { mutableStateOf(false) }
    val timePickerState = rememberTimePickerState()
    var showTimePicker by remember { mutableStateOf(false) }
    val dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    val timeFormatter = DateTimeFormatter.ofPattern("hh:mm a")
    Column(
        modifier = Modifier
            .wrapContentSize()
            .padding(start = 16.dp, bottom = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        Text(
            text = transaction.createdDate.format(dateFormatter),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Button(
            onClick = {
                showDatePicker = true
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(text = "Date Picker")
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 24.dp))

        Text(
            text = transaction.createdTime.format(timeFormatter),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Button(
            onClick = {
                showTimePicker = true
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(text = "Time Picker")
        }
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            val selectedDate = LocalDate.ofEpochDay(millis / (24 * 60 * 60 * 1000))
                            viewModel.updateCreatedDate(selectedDate)
                        }
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


    if (showTimePicker) {
        TimePickerDialog(
            onDismissRequest = { },
            confirmButton = {
                TextButton(
                    onClick = {
                        val selectedTime =
                            LocalTime.of(timePickerState.hour, timePickerState.minute)
                        viewModel.updateCreatedTime(selectedTime)
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyExtendedFab(viewModel: TransactionViewModel, transactionID: String?) {
    Box {
        var buttonText by remember {
            mutableStateOf("")
        }
        buttonText = if (transactionID.isNullOrEmpty()) {
            "SAVE"
        } else {
            "UPDATE"
        }
        ExtendedFloatingActionButton(
            onClick = {
                if (!transactionID.isNullOrEmpty()) {
                    viewModel.saveTransaction(true)
                } else {
                    viewModel.saveTransaction(false)
                }
            },
            text = { Text(buttonText, fontSize = 16.sp) },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.save_svgrepo_com),
                    contentDescription = "Add",
                    tint = MaterialTheme.colorScheme.tertiary
                )
            },
            modifier = Modifier
                .align(Alignment.BottomEnd),
            shape = RoundedCornerShape(12.dp),
            containerColor = Color(0xFFF42121),
            contentColor = MaterialTheme.colorScheme.tertiary
        )
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
            containerColor = if (isSelected) cyan800 else Color.LightGray
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
                    textAlign = TextAlign.Center,
                    color = Color.Black
                )
            }
        }
    }
}