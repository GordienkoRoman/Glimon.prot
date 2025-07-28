package com.example.glimonprot.presentation.bottomSheets

import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.glimonprot.presentation.bottomSheets.BottomSheetButtons
import stud.gilmon.presentation.components.CustomBottomSheetContainer
import stud.gilmon.presentation.components.CustomTextField
import stud.gilmon.presentation.components.LabelText

@Composable
fun ChangePasswordBottomSheet(
    showModalBottomSheet: MutableState<Boolean>,
    option: MutableState<String>,
    onDismissRequest: () -> Unit
) {
    val password = remember {
        mutableStateOf("")
    }
    if (showModalBottomSheet.value)
        CustomBottomSheetContainer(
            onDismissRequest = onDismissRequest
        ) {
            LabelText(text = "Change password")
            CustomTextField(
                label = "Current password",
                value = password.value
            )
            CustomTextField(
                label = "New password",
                value = password.value
            ) {
                password.value = it
            }
            CustomTextField(
                label = "Repeat new password",
                value = password.value
            )
            Divider(thickness = 1.dp, color = Color.White)
            BottomSheetButtons(val1 = option, val2 = password, onDismissRequest)
        }
}