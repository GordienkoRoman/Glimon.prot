package com.example.glimonprot.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.glimonprot.presentation.theme.TextFieldLabelColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomBottomSheetContainer(
    onDismissRequest: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {

    val skipPartially by remember { mutableStateOf(true) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = skipPartially)


   ModalBottomSheet(
        containerColor = MaterialTheme.colorScheme.onBackground,
        onDismissRequest = { onDismissRequest() },
        sheetState = bottomSheetState,
        scrimColor = BottomSheetDefaults.ScrimColor,
        dragHandle = { CustomDragHandle() },
    )
    {
        Column(
            Modifier
                .fillMaxWidth()
                //.windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Bottom))
                .padding(bottom = 30.dp, start = 10.dp, end = 10.dp)
                .background(MaterialTheme.colorScheme.onBackground),
            horizontalAlignment = CenterHorizontally
        ) {
            content()
        }
    }
}

val DockedDragHandleHeight = 4.0.dp
const val DockedDragHandleOpacity = 0.4f
val DockedDragHandleWidth = 22.0.dp
private val DragHandleVerticalPadding = 10.dp

@Preview
@Composable
fun CustomDragHandle(
    modifier: Modifier = Modifier,
    width: Dp = DockedDragHandleWidth,
    height: Dp = DockedDragHandleHeight,
    shape: Shape = MaterialTheme.shapes.medium,
    color: Color = TextFieldLabelColor
        .copy(DockedDragHandleOpacity),
) {
    Surface(
        modifier = modifier
            .padding(top = DragHandleVerticalPadding),
        color = color,
        shape = shape
    ) {
        Box(
            Modifier
                .size(
                    width = width,
                    height = height
                )
        )
    }
}