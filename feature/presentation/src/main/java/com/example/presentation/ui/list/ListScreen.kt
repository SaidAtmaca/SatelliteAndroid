package com.example.presentation.ui.list

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
fun ListScreen(
    navController: NavController,
    viewModel: ListViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    Box(Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Text("lİSTT")

        Button(onClick = {navController.navigate(Screen.DetailScreen.route)}) {
            Text("to detail")
        }
    }

}