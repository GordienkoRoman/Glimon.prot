package com.example.glimonprot.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp


@Composable
fun CustomList(
    radioOptions: List<String>,
    option: MutableState<String>,
    showModalBottomSheet: MutableState<Boolean>,
    onDismissRequest: () -> Unit = {}
) {
    Column(Modifier.selectableGroup()) {
        Text(
            text = "Choose coupon status",
            color = Color.LightGray,
            modifier = Modifier.padding(vertical = 15.dp)

        )
        HorizontalDivider(thickness = 1.dp, color = Color.LightGray)

        radioOptions.forEach { text ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .selectable(
                        selected = (text == option.value),
                        onClick = {
                            option.value = text
                            onDismissRequest()
                        },
                        role = Role.RadioButton
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = text,
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .weight(1f)
                )
                RadioButton(
                    selected = (text == option.value),
                    onClick = null,
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colorScheme.tertiary
                    )
                )

            }
        }
    }
    CustomCancelButton(
        text = "Cancel"
    ) {
        showModalBottomSheet.value = false
    }
}