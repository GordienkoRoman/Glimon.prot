package com.example.glimonprot.presentation.bottomSheets

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import stud.gilmon.presentation.components.CustomBottomSheetContainer
import stud.gilmon.presentation.components.CustomButton
import stud.gilmon.presentation.components.CustomText
import stud.gilmon.presentation.components.LabelText
import stud.gilmon.presentation.theme.TextFieldContainerColor
import stud.gilmon.presentation.theme.YellowGlimon

@Composable
fun ChangeLocationBottomSheet(
    showModalBottomSheet: MutableState<Boolean>,
    option: MutableState<String>,
    onDismissRequest: () -> Unit
) {
    val location = remember {
        mutableStateOf("")
    }
    if (showModalBottomSheet.value)
        CustomBottomSheetContainer(
            onDismissRequest = onDismissRequest
        ) {
            LabelText(text = "Choose location")
            CustomText(text = "You will be shown promotions for the selected location")
            Row() {
                CustomButton(
                    text = "Moscow", modifier = Modifier.weight(1f),
                    containerColor = TextFieldContainerColor,
                ) {
                    onDismissRequest()
                }
                Spacer(
                    modifier = Modifier
                        .size(5.dp)
                )
                CustomButton(
                    text = "Novosibirsk", modifier = Modifier.weight(1f),
                    containerColor = TextFieldContainerColor
                )
                {
                    onDismissRequest()
                }
            }
            Row {
                CustomButton(
                    text = "Samara", modifier = Modifier.weight(1f),
                    containerColor = TextFieldContainerColor,
                ) {
                    onDismissRequest()
                }
                Spacer(
                    modifier = Modifier
                        .size(5.dp)
                )
                CustomButton(
                    text = "Chelyabinsk", modifier = Modifier.weight(1f),
                    containerColor = TextFieldContainerColor
                )
                {
                    onDismissRequest()
                }
            }
            CustomButton(
                text = option.value,
                modifier = Modifier.fillMaxWidth(),
                containerColor = YellowGlimon,
            ) {
                onDismissRequest()
            }
        }
}