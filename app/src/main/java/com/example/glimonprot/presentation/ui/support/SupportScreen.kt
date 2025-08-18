package com.example.glimonprot.presentation.ui.support

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.example.glimonprot.presentation.components.CustomButton
import com.example.glimonprot.presentation.components.CustomText
import com.example.glimonprot.R
import com.example.glimonprot.presentation.components.LinkButton
import com.example.glimonprot.presentation.components.SocialNetworkIcon


@Composable
fun SupportScreen(onClick: ()->Unit) {
    val context = LocalContext.current
    Column(
        verticalArrangement = Arrangement.spacedBy(15.dp),
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .padding(horizontal = 25.dp, vertical = 20.dp)
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Suppot Service",
            fontSize = 30.sp,
            color = Color.White,
        )
        Image(
            painter = painterResource(id = R.drawable.support),
            contentDescription = "123",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
        )
        CustomText(text = "Any question or suggestions?")
        CustomButton(text = "Contact Support")
        {
            onClick()
        }
        CustomText(text = "Social Networks:")
        Row {
            SocialNetworkIcon(idIcon = R.drawable.vk_logo)
            Spacer(Modifier.width(15.dp))
            SocialNetworkIcon(idIcon = R.drawable.ok_logo)
        }
        CustomText(text = "Phone Number:")
        Button(onClick = {
            val call = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${R.string.support_phone_number}"))
            startActivity(context,call,null)
        },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            )) {
            Text(stringResource(R.string.support_phone_number),
            color= MaterialTheme.colorScheme.tertiary)
        }
        CustomText(text = "Working hours: 11:00-19:00")
        LinkButton(text = "About Company", icon = Icons.Filled.Home)
        LinkButton(text = "Place FeedItem", icon = Icons.Filled.Add)
        LinkButton(text = "Vacancies", icon = Icons.Filled.List)
        LinkButton(text = "FAQ", icon = Icons.Filled.Info)
    }
}
