package com.example.glimonprot.presentation.bottomSheets

import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.glimonprot.presentation.components.CustomBottomSheetContainer
import com.example.glimonprot.presentation.components.CustomTextField
import com.example.glimonprot.presentation.components.LabelText

@Composable
fun ChangePhoneNumberBottomSheet(
    showModalBottomSheet: MutableState<Boolean>,
    option: MutableState<String>,
    onDismissRequest: () -> Unit
) {
    val number = remember {
        mutableStateOf(option.value)
    }
    if (showModalBottomSheet.value)
        CustomBottomSheetContainer(
            onDismissRequest = onDismissRequest
        ) {
            LabelText(text = "Change phone number")
            Text(text = "No need to confirm phone number")
            CustomTextField(
                label = "New phone number",
                value = number.value
            ) {
                number.value = it
            }
            Divider(thickness = 1.dp, color = Color.White)
            BottomSheetButtons(val1 = option, val2 = number, onDismissRequest)
        }
}