package com.example.presentation.ui.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.presentation.ui.list.ErrorScreenContent
import com.example.presentation.ui.list.LoadingScreenContent


@Composable
fun DetailScreen(
    id:Int,
    name:String,
    viewModel: DetailViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val currentPosition by viewModel.currentPositionState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.fetchSatelliteDetail(context,id)
        viewModel.fetchSatellitePositions(context,id)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when (val state = uiState) {
                    is DetailUIState.Error -> {
                        val errorMessage = state.message
                        ErrorScreenContent(errorMessage)
                    }

                    is DetailUIState.Success -> {

                        val detail = state.detail

                        Column {
                            Text(name)
                            Text(detail.id.toString())
                            Text(detail.first_flight)
                            Text(detail.mass.toString())
                            Text(detail.height.toString())
                            Text(detail.cost_per_launch.toString())
                            Text("${currentPosition.posX},${currentPosition.posY}")
                        }
                    }

                    is DetailUIState.Loading -> {
                        LoadingScreenContent()
                    }
                }

            }
        }
    }
}