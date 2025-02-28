package com.example.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.presentation.ui.detail.DetailScreen
import com.example.presentation.ui.list.ListScreen
import com.saidatmaca.presentation.util.Screen


@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.ListScreen.route,
        modifier = Modifier.fillMaxSize()){



        composable(Screen.ListScreen.route){
            ListScreen(navController = navController,)
        }

        composable(Screen.DetailScreen.route){

            /** SharedViewModel eklenecek */
            DetailScreen(
                navController = navController,
                id= 1
            )
        }

    }
}