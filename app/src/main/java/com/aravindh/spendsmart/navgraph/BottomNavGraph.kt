package com.aravindh.spendsmart.navgraph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.aravindh.spendsmart.ui.screens.budgetscreen.BudgetScreen
import com.aravindh.spendsmart.ui.screens.filterscreen.FilterScreen

@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = BottomBarScreen.Budget.route) {
        composable(route = BottomBarScreen.Budget.route) {
            BudgetScreen()
        }
        composable(route = BottomBarScreen.Filter.route) {
            FilterScreen()
        }
    }

}