package stud.gilmon.presentation.bottomSheets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import stud.gilmon.presentation.components.CustomBottomSheetContainer
import stud.gilmon.presentation.components.CustomList

@Composable
fun ChooseFamilyStatusBottomSheet(
    showModalBottomSheet: MutableState<Boolean>,
    option: MutableState<String>,
    onDismissRequest: () -> Unit
) {
    if (showModalBottomSheet.value)
        CustomBottomSheetContainer(
            onDismissRequest = onDismissRequest
        ) {
            CustomList(
                listOf("Single", "Actively looking", "Married", "It's complicated"),
                option,
                showModalBottomSheet,
                onDismissRequest
            )
        }
}