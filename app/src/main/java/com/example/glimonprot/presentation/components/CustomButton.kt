package com.example.glimonprot.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.glimonprot.presentation.theme.RedGlimon
import com.example.glimonprot.presentation.theme.SpacerColor
import com.example.glimonprot.presentation.theme.TextFieldLabelColor

@Composable
fun CustomButton(
    text: String,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.onBackground,
    textColor:Color = Color.White,
    onClick: () -> Unit = {}
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor
        ),
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .fillMaxWidth()

        //modifier = Modifier.clip(RoundedCornerShape(1.dp))
    ) {

        Text(
            text = text,
            color = textColor,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun CustomCancelButton(text: String, modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .fillMaxWidth()

    ) {
        Text(
            text = text,
            color = Color.LightGray,
            modifier = Modifier.padding(vertical = 5.dp)
        )
    }
}

@Composable
fun LinkButton(text: String, icon: ImageVector, onClick: () -> Unit = {}) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.onBackground
        ),
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSecondary
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = text,
            color = Color.White,
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSecondary
        )
    }
}

@Composable
fun SelectButton(
    labelText: String,
    text: String,
    containerColor: Color=MaterialTheme.colorScheme.onBackground,
    underline: Boolean = false,
    icon:ImageVector?=null,
    onClick: () -> Unit = {}
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor
        ),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        if(icon!=null)
        {
            Icon(
                modifier = Modifier.size(26.dp),
                imageVector = icon,
                contentDescription = null,
                tint = RedGlimon
            )
            Spacer(modifier = Modifier.width(10.dp))
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = labelText,
                color = TextFieldLabelColor,
            )
            Text(
                text = text,
                color = Color.White,
            )
        }

        Icon(
            modifier = Modifier.size(15.dp),
            imageVector = Icons.Filled.KeyboardArrowDown,
            contentDescription = null,
            tint = TextFieldLabelColor
        )
    }
    if (underline)
        Spacer(
            Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
                .size(1.dp)
                .background(SpacerColor)
        )
}