package com.example.glimonprot.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun CustomNavigationBar(
    modifier: Modifier = Modifier,
    tonalElevation: Dp = NavigationBarDefaults.Elevation,
    content: @Composable RowScope.() -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.onBackground,
        contentColor = MaterialTheme.colorScheme.onBackground,
        tonalElevation = tonalElevation,
        modifier = modifier
            .padding(bottom = 30.dp, start = 70.dp,end = 70.dp)
            .height(50.dp)
            .clip(shape = RoundedCornerShape(25.dp))
    ) {
        Row(
            modifier = Modifier,
            content = content
        )
    }
}