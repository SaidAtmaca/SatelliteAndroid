package com.example.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.presentation.theme.CornerRound
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce


@OptIn(FlowPreview::class)
@Composable
fun StandardSearchBar(
    modifier: Modifier = Modifier,
    hint: String = "Ara...",
    onSearch: (String) -> Unit,
    onCleared: () -> Unit,
    focusRequester: FocusRequester
) {
    var searchQuery by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val searchDebounceTime = 600L
    val searchFlow = remember { MutableStateFlow("") }

    LaunchedEffect(searchFlow) {
        searchFlow
            .debounce(searchDebounceTime)
            .collect { query ->
                if (query.isNotEmpty()) {
                    onSearch(query)
                }
            }
    }

    OutlinedTextField(
        value = searchQuery,
        onValueChange = { newQuery ->
            searchQuery = newQuery
            searchFlow.tryEmit(newQuery) // yeni değeri StateFlow'a gönder
        },
        modifier = modifier
            .fillMaxWidth()
            .focusRequester(focusRequester)
            .padding(8.dp),
        label = { Text(hint, style = MaterialTheme.typography.bodyLarge) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                focusManager.clearFocus()
                onSearch(searchQuery)
            }
        ),
        trailingIcon = {
            if (searchQuery.isNotEmpty()) {
                IconButton(onClick = {
                    searchQuery = ""
                    onCleared()
                }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Clean"
                    )
                }
            }
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Icon"
            )
        },
        textStyle = MaterialTheme.typography.titleSmall,
        shape = RoundedCornerShape(CornerRound.largeCurveRound)
    )
}