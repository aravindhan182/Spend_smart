package com.aravindh.spendsmart.navgraph

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.aravindh.spendsmart.ui.screens.addexpenseandincomescreen.AddExpenseAndIncomeScreen
import com.aravindh.spendsmart.ui.screens.calendar.CalendarScreen
import com.aravindh.spendsmart.ui.screens.daily.DailyScreen
import com.aravindh.spendsmart.ui.screens.montlyscreen.MonthlyScreen
import com.aravindh.spendsmart.ui.screens.totalscreen.TotalScreen

@Composable
fun BottomNavGraph(navController: NavHostController, currentIndex: MutableIntState) {
    NavHost(navController = navController, startDestination = BottomBarScreen.Daily.route) {
        composable(route = BottomBarScreen.Daily.route) {
            DailyScreen(navController = navController)
        }
        composable(route = BottomBarScreen.Monthly.route) {
            MonthlyScreen(navController = navController)
        }
        composable(route = BottomBarScreen.Calendar.route) {
            CalendarScreen(navController = navController)
        }
        composable(route = BottomBarScreen.Total.route) {
            TotalScreen()
        }
        // Add a composable for the FAB button destination
        composable(route = "fabRoute") {
            // Content for FAB button destination
            // You can replace PlaceholderScreen with your actual content
            AddExpenseAndIncomeScreen(navController = navController)
        }
    }

}