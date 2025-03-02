package com.example.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.presentation.navigation.Navigation
import com.example.presentation.theme.SatelliteAndroidTheme

@Composable
fun SatelliteApp(
) {
    SatelliteAndroidTheme(dynamicColor = false) {
        val navController = rememberNavController()
        Scaffold(Modifier.fillMaxSize()) {
            Box(modifier = Modifier.padding(it)){
                Navigation(navController = navController)
            }
        }
    }
}
