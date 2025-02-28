package com.example.presentation.ui.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.model.SatelliteModel
import com.example.presentation.R
import com.example.presentation.components.StandardSearchBar
import com.example.presentation.theme.mediumPadding
import com.example.presentation.theme.smallPadding


@Composable
fun ListScreen(
    onClickSatellite :(String,Int)->Unit,
    viewModel: ListViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        viewModel.fetchSatelliteList(context)
    }

    Scaffold(
        modifier = Modifier
            .background(Color.LightGray)
            .fillMaxSize(),
        topBar = {
            StandardSearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(smallPadding),
                hint = stringResource(R.string.search),
                onSearch = { query ->
                    viewModel.updateSearchQuery(query)
                },
                onCleared = {
                    viewModel.updateSearchQuery("")
                },
                focusRequester = focusRequester
            )

        }
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { focusManager.clearFocus() },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when (val state = uiState) {
                    is ListUIState.Error -> {
                        val errorMessage = state.message
                        ErrorScreenContent(errorMessage)
                    }

                    is ListUIState.Success -> {

                        val list = state.filteredList

                        if (list.isEmpty()) {
                            NoDataLayout()
                        } else {
                            SuccessScreenContent(
                                list = list,
                                onClickSatellite = { name, id ->
                                    onClickSatellite(name, id)
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
fun SuccessScreenContent(list: List<SatelliteModel>, onClickSatellite: (String, Int) -> Unit) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(list) { eachSatellite ->
            val isLast = eachSatellite == list.last()

            SatelliteCard(eachSatellite,isLast) { clickedSatellite ->
                onClickSatellite(clickedSatellite.name, clickedSatellite.id)
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
fun SatelliteCard(
    model: SatelliteModel,
    isLastItem: Boolean,
    cardClicked: (SatelliteModel) -> Unit
) {

    val circleBackground = if (model.active) Color.Green else Color.Red
    val textColor = if (model.active) Color.Black else Color.LightGray
    val activeString =
        if (model.active) stringResource(R.string.active) else stringResource(R.string.passive)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(intrinsicSize = IntrinsicSize.Max)
            .clickable {
                cardClicked(model)
            },
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .padding(vertical = smallPadding, horizontal = mediumPadding)
                    .fillMaxSize()
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .padding(smallPadding)
                        .clip(CircleShape)
                        .background(color = circleBackground)
                )
                Column(
                    modifier = Modifier.padding(smallPadding),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = model.name,
                        color = textColor,
                        style = MaterialTheme.typography.labelMedium
                    )
                    Text(
                        text = activeString,
                        color = textColor,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
            AnimatedVisibility(visible = !isLastItem) {
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = mediumPadding),
                    thickness = 1.dp,
                    color = Color.DarkGray
                )
            }

        }

    }

}