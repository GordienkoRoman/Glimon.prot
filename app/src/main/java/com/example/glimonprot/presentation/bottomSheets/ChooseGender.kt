package com.example.glimonprot.presentation.bottomSheets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.example.glimonprot.presentation.components.CustomBottomSheetContainer
import com.example.glimonprot.presentation.components.CustomList

@Composable
fun ChooseGenderBottomSheet(
    showModalBottomSheet: MutableState<Boolean>,
    option: MutableState<String>,
    onDismissRequest: () -> Unit
) {
    if (showModalBottomSheet.value)
        CustomBottomSheetContainer(
            onDismissRequest = onDismissRequest
        ) {
            CustomList(
                listOf("Not Specified", "Male", "Female"),
                option,
                showModalBottomSheet,
                onDismissRequest
            )
        }
}