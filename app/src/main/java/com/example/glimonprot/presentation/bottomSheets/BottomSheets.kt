package com.example.glimonprot.presentation.bottomSheets

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.glimonprot.presentation.components.CustomButton
import com.example.glimonprot.presentation.theme.TextFieldContainerColor
import com.example.glimonprot.presentation.theme.YellowGlimon


@Composable
fun BottomSheetButtons(
    val1: MutableState<String>,
    val2: MutableState<String>,
    onDismissRequest: () -> Unit
) {
    Row {
        CustomButton(
            text = "Save", modifier = Modifier.weight(1f),
            containerColor = YellowGlimon,
            textColor = Color.Black
        ) {
            val1.value = val2.value
            onDismissRequest()
        }
        Spacer(
            modifier = Modifier
                .size(5.dp)
        )
        CustomButton(
            text = "Cancel", modifier = Modifier.weight(1f),
            containerColor = TextFieldContainerColor
        )
        {
            onDismissRequest()
        }
    }
}