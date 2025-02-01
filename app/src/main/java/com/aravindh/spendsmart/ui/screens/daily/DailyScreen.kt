package com.aravindh.spendsmart.ui.screens.daily

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.aravindh.spendsmart.R
import com.aravindh.spendsmart.ui.screens.model.ExpenseOrIncomeMutableView


@Composable
fun DailyScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .background(Color(0xFFF4EAF0))
            .fillMaxSize()
            .padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column {

            val ls = listOf<ExpenseOrIncomeMutableView>(
                ExpenseOrIncomeMutableView(
                    id = 1,
                    "INCOME",
                    "50.00",
                    category = ImageVector.vectorResource(id = R.drawable.reshot_icon_money_transport_zx3wfh7j68),
                    date = "june 24, 2024",
                    time = "05:45 pm",
                    paymentMethod = "Debit card",
                    notes = "Hi I am mrcho"
                ),
                ExpenseOrIncomeMutableView(
                    id = 2,
                    "INCOME",
                    "340.00",
                    category = ImageVector.vectorResource(id = R.drawable.reshot_icon_computer_dollar_nfvhye9jp4),
                    date = "april 30, 2024",
                    time = "03:23 am",
                    paymentMethod = "Paytm",
                    notes = "Hi, I am stabbin"
                ),
                ExpenseOrIncomeMutableView(
                    id = 3,
                    "EXPENSE",
                    "540.00",
                    category = ImageVector.vectorResource(id = R.drawable.reshot_icon_money_transport_zx3wfh7j68),
                    date = "march 12, 2024",
                    time = "12:45 pm",
                    paymentMethod = "debit card",
                    notes = "Hi, I am kandha"
                ),
                ExpenseOrIncomeMutableView(
                    id = 4,
                    "INCOME",
                    "5320.00",
                    category = ImageVector.vectorResource(id = R.drawable.reshot_icon_money_transport_zx3wfh7j68),
                    date = "january 12, 2025",
                    time = "04:05 am",
                    paymentMethod = "gift cards",
                    notes = "Hi, I am kali"
                ),
                ExpenseOrIncomeMutableView(
                    id = 5,
                    "INCOME",
                    "130.00",
                    category = ImageVector.vectorResource(id = R.drawable.reshot_icon_computer_dollar_nfvhye9jp4),
                    date = "january 20, 2025",
                    time = "04:23 pm",
                    paymentMethod = "voucher",
                    notes = "Hi I am aravindh"
                ),
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu"
                )
                Text(
                    text = "Today Record",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.h6
                )
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            }
            ShutterView()
            LazyColumn {
                items(ls) { item ->
                    IncomeOrExpenseRecyclerViewCard(item = item)
                }
            }
        }
    }
}

@Composable
fun IncomeOrExpenseRecyclerViewCard(item: ExpenseOrIncomeMutableView) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF9e8e8)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            Column(
                modifier = Modifier
                    .weight(0.6f)
                    .fillMaxHeight()
                    .padding(8.dp)
            ) {
                AssistChip(
                    onClick = { },
                    label = { Text(item.title, color = Color.Black) },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = Color.LightGray
                    ),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(2.dp, Color.LightGray)
                )
                Text(text = item.notes, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                Text(text = item.value)
                Button(
                    onClick = { /* TODO */ },
                    colors = ButtonDefaults.outlinedButtonColors(
                        backgroundColor = Color.White,
                        contentColor = Color.Black
                    )
                ) {
                    Text(text = "Edit")
                }
            }

            Column(
                modifier = Modifier
                    .weight(0.4f)
                    .fillMaxHeight()
                    .background(color = Color(0xFFF5BDBD))
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    imageVector = item.category,
                    contentDescription = "category image",
                    modifier = Modifier.size(100.dp)
                )
            }
        }
    }
}


@Composable
fun ShutterView() {
    var isExpanded by remember { mutableStateOf(false) }
    val transition = updateTransition(targetState = isExpanded, label = "Shutter Animation")

    val offsetY by transition.animateDp(label = "Offset Y") { expanded ->
        if (expanded) 0.dp else (-100).dp
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        IconButton(
            onClick = { isExpanded = !isExpanded },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            androidx.compose.material.Icon(
                imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = "Toggle Shutter"
            )
        }
        AnimatedVisibility(visible = isExpanded) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .offset(y = offsetY),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(4.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            AssistChip(
                                onClick = {},
                                modifier = Modifier.weight(1f),
                                label = {
                                    Text(
                                        "Income",
                                        fontSize = 20.sp,
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold
                                    )
                                },
                                colors = AssistChipDefaults.assistChipColors(
                                    containerColor = Color(
                                        0xFF095b09
                                    )
                                ),
                                trailingIcon = {
                                    IconButton(onClick = {}) {
                                        Icon(
                                            imageVector = Icons.Default.CheckCircle,
                                            contentDescription = "Close",
                                            tint = Color.White
                                        )
                                    }
                                }
                            )
                            AssistChip(
                                onClick = {},
                                modifier = Modifier.weight(1f),
                                label = {
                                    Text(
                                        "Expense",
                                        fontSize = 20.sp,
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold
                                    )
                                },
                                colors = AssistChipDefaults.assistChipColors(
                                    containerColor = Color(
                                        0xFFD12410
                                    )
                                ),
                                trailingIcon = {
                                    IconButton(onClick = {}) {
                                        Icon(
                                            imageVector = Icons.Default.Warning,
                                            contentDescription = "Close",
                                            tint = Color.White
                                        )
                                    }
                                }
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .wrapContentHeight(),
                                contentAlignment = Alignment.TopCenter
                            ) {
                                Text(
                                    text = "12,0000",
                                    textAlign = TextAlign.Start,
                                    color = Color.Black,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .weight(1f) // Equal width
                                    .wrapContentHeight(),
                                contentAlignment = Alignment.TopCenter
                            ) {
                                Text(
                                    text = "13,0000",
                                    textAlign = TextAlign.Start,
                                    color = Color.Black,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        Box(
                            modifier = Modifier
                                .padding(start = 8.dp, end = 8.dp)
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            contentAlignment = Alignment.TopCenter
                        ) {
                            Text(
                                text = "Please be careful in your expense :)",
                                textAlign = TextAlign.Center,
                                fontStyle = FontStyle.Italic, color = Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}

