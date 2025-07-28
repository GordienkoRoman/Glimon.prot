package stud.gilmon.presentation.ui.support

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.StateFlow
import stud.gilmon.data.local.entities.UsersEntity
import stud.gilmon.presentation.components.CustomTextField2
import stud.gilmon.presentation.components.TextWithLink

@Composable
fun ContactSupportScreen(
    user: StateFlow<UsersEntity>,
    onclick: () -> Unit = {}
) {
    val name = remember{ mutableStateOf(user.value.firstName) }
    val number = remember{ mutableStateOf(user.value.number) }
    val mail = remember{ mutableStateOf(user.value.mail) }
    val message = remember{ mutableStateOf("") }
    Column(
        verticalArrangement = Arrangement.spacedBy(15.dp),
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.onBackground)
            .padding(horizontal = 25.dp, vertical = 20.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        TopBar(onclick)
        CustomTextField2(label = "Name", value = name.value){
            name.value=it
        }
        CustomTextField2(label = "Number", value = number.value)
        {
            number.value=it
        }
        CustomTextField2(label = "E-mail", value = mail.value){
            mail.value=it
        }
        CustomTextField2(label = "Enter your message", value = message.value){
            message.value=it
        }
        Column( verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text("By Clicking \"Ok\" button, you agree", color = Color.White)
            TextWithLink(textLink = "the user agreement", textBefore = "to ") {
                
            }
            TextWithLink(textLink = "the privacy policy", textBefore = "and ") {
                
            }
        }


    }

}

@Composable
private fun TopBar(onclick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.onBackground),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(onClick = onclick,colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.onBackground
        )) {
            Icon(
                Icons.Filled.ArrowBack,
                tint = Color.White,
                contentDescription = "back"
            )
        }

        Text(
            text = "Feedback",
            modifier = Modifier.weight(1f),
            color = Color.White,
            fontSize = 16.sp, textAlign = TextAlign.Center
        )
        Text(text = "Ok", color = MaterialTheme.colorScheme.tertiary)
    }
}
