package com.example.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.presentation.theme.mediumPadding


@Composable
fun ValueWithDescriptionComponent(description : String, value: String) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(mediumPadding),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "$description:",
            style = MaterialTheme.typography.labelMedium)

        Text(text = value,
            style = MaterialTheme.typography.bodyMedium,)
    }

}