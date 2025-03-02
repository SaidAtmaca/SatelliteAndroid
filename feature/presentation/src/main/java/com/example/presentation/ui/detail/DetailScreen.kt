package com.example.presentation.ui.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.common.formatDate
import com.example.common.formatNumber
import com.example.model.PositionModel
import com.example.model.SatelliteDetailModel
import com.example.presentation.components.ValueWithDescriptionComponent
import com.example.presentation.theme.largePadding
import com.example.presentation.theme.mediumPadding
import com.example.presentation.theme.smallPadding
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
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                when (val state = uiState) {
                    is DetailUIState.Error -> {
                        val errorMessage = state.message
                        ErrorScreenContent(errorMessage)
                    }

                    is DetailUIState.Success -> {

                        val detail = state.detail
                        SuccessDetailContent(detail=detail, name = name, currentPosition = currentPosition)


                    }

                    is DetailUIState.Loading -> {
                        LoadingScreenContent()
                    }
                }

            }
        }
    }
}

@Composable
fun SuccessDetailContent(detail:SatelliteDetailModel,name:String,currentPosition:PositionModel) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(smallPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {

        NameAndDateComponent(name = name, date = detail.first_flight)
        HeightMassCostPosition(detail = detail, currentPosition = currentPosition)
    }
}

@Composable
fun NameAndDateComponent(name:String,date:String) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(largePadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = name,
            style =MaterialTheme.typography.displayMedium,
            modifier = Modifier
                .padding(mediumPadding))

        Text(text = formatDate(date),
            style =MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            modifier = Modifier
                .padding(smallPadding))
    }
}

@Composable
fun HeightMassCostPosition(detail:SatelliteDetailModel,currentPosition:PositionModel) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(largePadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ValueWithDescriptionComponent(description = "Height/Mass", value ="${detail.height}/${detail.mass}" )

        ValueWithDescriptionComponent(description = "Cost", value = formatNumber(detail.cost_per_launch))

        ValueWithDescriptionComponent(description = "Last Position", value ="(${currentPosition.posX},${currentPosition.posY})" )
    }
}

