package com.example.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.presentation.ui.detail.DetailScreen
import com.example.presentation.ui.list.ListScreen
import com.saidatmaca.core.viewmodel.SharedViewModel
import com.saidatmaca.presentation.util.Screen


@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController,
        startDestination = "main",
        modifier = Modifier.fillMaxSize()){

        navigation(
            startDestination = Screen.ListScreen.route,
            route = "main"
        ) {
            composable(Screen.ListScreen.route) { entry ->
                val viewModel = entry.sharedViewModel<SharedViewModel>(navController)
                ListScreen(navController = navController,
                    onClickSatellite = { clickedSatellite ->
                        viewModel.updateState(clickedSatellite)
                        navController.navigate(Screen.DetailScreen.route)
                    })
            }

            composable(Screen.DetailScreen.route) { entry ->

                val viewModel = entry.sharedViewModel<SharedViewModel>(navController)
                val id= viewModel.sharedState.value

                DetailScreen(
                    navController = navController,
                    id = id
                )
            }
        }
    }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(
    navController: NavHostController
) : T {
    val navGraphRoute = destination.parent?.route ?: return viewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return viewModel(parentEntry)
}