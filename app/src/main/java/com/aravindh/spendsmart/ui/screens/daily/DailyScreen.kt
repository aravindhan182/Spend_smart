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
import androidx.compose.material3.OutlinedCard
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
import com.aravindh.spendsmart.ui.screens.model.TransactionMutableView


@Composable
fun DailyScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .background(androidx.compose.material3.MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu",
                    tint = androidx.compose.material3.MaterialTheme.colorScheme.tertiary
                )
                Text(
                    text = "Today Record",
                    fontWeight = FontWeight.Bold,
                    color = androidx.compose.material3.MaterialTheme.colorScheme.tertiary,
                    style = MaterialTheme.typography.h6
                )
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = androidx.compose.material3.MaterialTheme.colorScheme.tertiary
                )
            }
            ShutterView()
         /*   LazyColumn {
                items(ls) { item ->
                    IncomeOrExpenseRecyclerViewCard(item = item)
                }
            }*/
        }
    }
}

/*@Composable
fun IncomeOrExpenseRecyclerViewCard(item: TransactionMutableView) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = androidx.compose.material3.MaterialTheme.colorScheme.secondary),
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
                    .background(color = androidx.compose.material3.MaterialTheme.colorScheme.secondary)
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
                    onClick = { *//* TODO *//* },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.primary, // Background color
                        contentColor = androidx.compose.material3.MaterialTheme.colorScheme.onPrimary  // Text/Icon color
                    )
                ) {
                    Text(text = "Edit")
                }
            }

            Column(
                modifier = Modifier
                    .weight(0.4f)
                    .fillMaxHeight()
                    .background(color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface)
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
}*/


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
                contentDescription = "Toggle Shutter",
                tint = androidx.compose.material3.MaterialTheme.colorScheme.tertiary
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
                    colors = CardDefaults.cardColors(containerColor = androidx.compose.material3.MaterialTheme.colorScheme.primary),
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
                                fontStyle = FontStyle.Italic
                            )
                        }
                    }
                }
            }
        }
    }
}

