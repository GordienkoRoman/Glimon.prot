package com.example.glimonprot.presentation.bottomSheets

import androidx.compose.material3.Divider
import androidx.compose.material3.Text
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
fun ChangeEmailBottomSheet(
    showModalBottomSheet: MutableState<Boolean>,
    option: MutableState<String>,
    onDismissRequest: () -> Unit
) {
    val mail = remember {
        mutableStateOf(option.value)
    }

    if (showModalBottomSheet.value)
        CustomBottomSheetContainer(
            onDismissRequest = onDismissRequest
        ) {
            LabelText(text = "Change e-mail")
            Text(text = "You will get e-mail for confirmation new e-mail address")
            CustomTextField(
                label = "New e-mail",
                value = mail.value
            ) {
                mail.value = it
            }

            Divider(thickness = 1.dp, color = Color.White)
            BottomSheetButtons(val1 = option, val2 = mail, onDismissRequest)
        }
}

