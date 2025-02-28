package com.example.presentation.ui.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.saidatmaca.presentation.util.Screen


@Composable
fun DetailScreen(
    navController: NavController,
    id:Int,
    viewModel: DetailViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    Box(Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Text("Detail")
        Button(onClick = {navController.navigate(Screen.ListScreen.route)}) {
            Text("to list")
        }
    }
}