package com.example.presentation.ui.list

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.model.SatelliteModel
import com.example.presentation.R
import com.example.presentation.theme.CornerRound


@Composable
fun ListScreen(
    navController: NavController,
    onClickSatellite :(Int)->Unit,
    viewModel: ListViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.fetchSatelliteList(context)
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
                    is ListUIState.Error -> {
                        val errorMessage = state.message
                        ErrorScreenContent(errorMessage)
                    }

                    is ListUIState.Success -> {

                        val list = state.satelliteList

                        if (list.isEmpty()) {
                            NoDataLayout()
                        } else {
                            SuccessScreenContent(navController = navController,
                                list = list,
                                onClickSatellite = { id->
                                    onClickSatellite(id)
                                })
                        }
                    }

                    is ListUIState.Loading -> {
                        LoadingScreenContent()
                    }
                }

            }
        }
    }

}

@Composable
fun LoadingScreenContent() {

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center){


        CircularProgressIndicator(modifier = Modifier.size(48.dp))
    }

}

@Composable
fun SuccessScreenContent(navController: NavController,list:List<SatelliteModel>,onClickSatellite: (Int) -> Unit) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()){
            items(list) { eachSatellite ->

              SatelliteCard(eachSatellite){ clickedSatellite ->
                  Log.e("clickedCard",clickedSatellite.toString())
                  onClickSatellite(clickedSatellite.id)
              }
            }

        }

}

@Composable
fun ErrorScreenContent(message:String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(imageVector = Icons.Default.Warning,
            contentDescription = "",
            modifier = Modifier.size(36.dp))

        Text(text = message)
    }
}

@Composable
fun NoDataLayout() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Text(text = stringResource(R.string.dataBulunamadi))
    }
}

@Composable
fun SatelliteCard(model: SatelliteModel, cardClicked:(SatelliteModel)->Unit) {

    Card(onClick = {
        cardClicked(model)
    },
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(intrinsicSize = IntrinsicSize.Max),
        shape = RoundedCornerShape(CornerRound.smallCurveRound),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 5.dp)
        ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = model.name)
        }
    }

}