package com.example.glimonprot.presentation.bottomSheets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.glimonprot.presentation.components.CustomBottomSheetContainer
import com.example.glimonprot.presentation.components.CustomButton
import com.example.glimonprot.presentation.components.CustomTextField
import com.example.glimonprot.presentation.components.LabelText
import com.example.glimonprot.presentation.theme.YellowGlimon
import com.example.glimonprot.presentation.ui.login.CloseButton

@Composable
fun WriteReviewBottomSheet(
    showModalBottomSheet: MutableState<Boolean>,
    option: MutableState<String>,
    onDismissRequest: (String) -> Unit
) {
    val review = remember {
        mutableStateOf(option.value)
    }

    if (showModalBottomSheet.value)
        CustomBottomSheetContainer(
            onDismissRequest = {onDismissRequest("")}
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.onBackground)
                    .padding(start = 15.dp, end = 15.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    LabelText(text = "Write review")
                    Box(
                        contentAlignment = Alignment.BottomEnd,
                        modifier = Modifier.fillMaxWidth()
                    )
                    {
                        CloseButton { onDismissRequest("") }
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 15.dp)
                    .background(MaterialTheme.colorScheme.onBackground)
            )
            {
                Divider(thickness = 1.dp, color = Color.White)
                CustomTextField(
                    label = "Tell us about this promotion...",
                    value = review.value,
                    minLines = 10
                ) {
                    review.value = it
                }
                Column(
                    verticalArrangement = Arrangement.Bottom,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CustomButton(
                        text = "Send",
                        textColor = Color.Black,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        containerColor = YellowGlimon
                    )
                    {
                        onDismissRequest(review.value)
                    }
                }
            }
        }
}