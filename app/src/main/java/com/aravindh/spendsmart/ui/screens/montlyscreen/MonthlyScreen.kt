package com.aravindh.spendsmart.ui.screens.montlyscreen

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.flowlayout.FlowCrossAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import java.util.Calendar

@Composable
fun MonthlyScreen(navController: NavController) {
    var visible by remember {
        mutableStateOf(false)
    }
    var date by remember {
        mutableStateOf("")
    }

    var showDialog by remember {
        mutableStateOf(true)
    }

    val currentMonth = Calendar.getInstance().get(Calendar.MONTH)
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MonthPicker(
            visible = visible,
            currentMonth = currentMonth.toString(),
            currentYear = currentYear,
            confirmButtonCLicked = { month, year ->
                date = "$month/$year"
                visible = false
            },
            cancelClicked = {
                visible = false

            }
        )
        TextButton(onClick = { visible = true }) {
            Text(text = "Select Month")
        }
        Text(text = date)
    }
}


@Composable
fun MonthPicker(
    visible: Boolean,
    currentMonth: String,
    currentYear: Int,
    confirmButtonCLicked: (Int, Int) -> Unit,
    cancelClicked: () -> Unit
) {
    val months = listOf(
        "JAN",
        "FEB",
        "MAR",
        "APR",
        "MAY",
        "JUN",
        "JUL",
        "AUG",
        "SEP",
        "OCT",
        "NOV",
        "DEC"
    )
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
                            fontWeight = FontWeight.Bold
                        )
                        Icon(
                            imageVector = Icons.Rounded.KeyboardArrowDown,
                            contentDescription = null,
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
                            .background(Color.White)
                    ) {
                        FlowRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White),
                            mainAxisSpacing = 16.dp,
                            crossAxisSpacing = 16.dp,
                            mainAxisAlignment = MainAxisAlignment.Center,
                            crossAxisAlignment = FlowCrossAxisAlignment.Center
                        ) {

                            months.forEach {
                                Box(
                                    modifier = Modifier
                                        .size(60.dp)
                                        .clickable(
                                            indication = null,
                                            interactionSource = interactionSource,
                                            onClick = { month = it })
                                        .background(color = Color.White),
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
                                                color = if (month == it) Color.Blue else Color.White,
                                                shape = CircleShape
                                            )
                                    )
                                    Text(
                                        text = it,
                                        color = if (month == it) Color.White else Color.Black
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
                                months.indexOf(month) + 1, year
                            )
                        },
                        shape = CircleShape,
                        border = BorderStroke(1.dp, color = Color.Blue),
                        colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.White)
                    ) {
                        Text(
                            text = "OK",
                            color = Color.Blue,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                }
            })
    }
}