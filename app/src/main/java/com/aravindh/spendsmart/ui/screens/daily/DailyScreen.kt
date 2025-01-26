package com.aravindh.spendsmart.ui.screens.daily

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


@Composable
fun DailyScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
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
    }
}



