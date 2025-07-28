package stud.gilmon.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import stud.gilmon.presentation.theme.DatePickerGray
import stud.gilmon.presentation.theme.TextFieldContainerColor
import stud.gilmon.presentation.theme.TextFieldLabelColor

@Preview
@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    readOnly: Boolean = false,
    label: String = "label",
    minLines:Int =1,
    onValueChange: (String) -> Unit = {}
) {
    TextField(
        label = { Text(label, color = TextFieldLabelColor) },
        value = value,
        onValueChange = onValueChange,
        readOnly = readOnly,
        colors = TextFieldDefaults.colors(
            unfocusedTextColor = MaterialTheme.colorScheme.onSecondary,
            focusedTextColor = MaterialTheme.colorScheme.onSecondary,
            focusedContainerColor = MaterialTheme.colorScheme.onSurface,
            unfocusedContainerColor = MaterialTheme.colorScheme.onSurface,
            disabledIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            unfocusedLabelColor = TextFieldLabelColor,

            ),
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        minLines = minLines
    )
}
@Composable
fun CustomTextField2(
    modifier: Modifier = Modifier,
    value: String = "",
    readOnly: Boolean = false,
    label: String = "label",
    onValueChange: (String) -> Unit = {}
) {
    TextField(
        label = { Text(label, color = TextFieldLabelColor) },
        value = value,
        onValueChange = onValueChange,
        readOnly = readOnly,
        colors = TextFieldDefaults.colors(
            unfocusedTextColor = Color.White,
            focusedTextColor = Color.White,
            focusedContainerColor = MaterialTheme.colorScheme.onSurface,
            unfocusedContainerColor = MaterialTheme.colorScheme.onSurface,
            disabledIndicatorColor = DatePickerGray,
            focusedIndicatorColor = DatePickerGray,
            unfocusedIndicatorColor = DatePickerGray,
            unfocusedLabelColor = TextFieldLabelColor,

            ),
        modifier = modifier
            .fillMaxWidth(),
    )
}
@Composable
fun CustomText(text: String, modifier: Modifier = Modifier, fontSize: TextUnit = TextUnit.Unspecified,textColor:Color = Color.White) {
    Text(
        text,
        color = textColor,
        modifier = modifier.padding(
            horizontal = 15.dp
        ),
        fontSize = fontSize
    )
}

@Composable
fun LabelText(text: String, modifier: Modifier = Modifier) {
    Text(
        text,
        fontSize = 25.sp,
        color = Color.White,
        modifier = modifier.padding(
            horizontal = 20.dp
        )
    )
}

@Composable
fun TextWithLink(textBefore:String="",textAfter:String="",textLink:String,onClick:()->Unit){
    Row {
        Text(text = textBefore, color = Color.White)
        Text(text = textLink,modifier = Modifier.clickable { onClick() },color = MaterialTheme.colorScheme.tertiary)
        Text(text = textAfter, color = Color.White)
    }

}