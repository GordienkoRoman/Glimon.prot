package com.example.glimonprot.presentation.ui.login

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.glimonprot.di.viewModelFactory.ViewModelFactory
import com.google.gson.Gson
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationResponse
import com.example.glimonprot.R
import com.example.glimonprot.data.oauth.GithubAuthConfig
import com.example.glimonprot.data.oauth.MailAuthConfig
import com.example.glimonprot.presentation.components.CustomText
import com.example.glimonprot.presentation.components.CustomTextField
import com.example.glimonprot.presentation.components.LabelText
import com.example.glimonprot.presentation.components.SocialNetworkIcon
import com.example.glimonprot.presentation.components.TextWithLink
import com.example.glimonprot.presentation.theme.BlueVK
import com.example.glimonprot.presentation.theme.OrangeOdnoklassniki
import com.example.glimonprot.presentation.theme.TextFieldLabelColor
import com.example.glimonprot.presentation.theme.YellowGlimon
import com.example.glimonprot.presentation.ui.Screen
import example.glimonprot.presentation.ui.login.LoginViewModel
import stud.gilmon.base.utils.launchAndCollectIn

@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModelFactory: ViewModelFactory,
    onClose: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)
    val viewModel: LoginViewModel = viewModel(factory = viewModelFactory)
    val getAuthResponse = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(), onResult = {
            if (it.data != null) {
                val dataIntent = it.data!!
                handleAuthResponseIntent(dataIntent, viewModel = viewModel)
            }
        })

    SideEffect {
        viewModel.openAuthPageFlow.launchAndCollectIn(lifecycleOwner.value) {
            getAuthResponse.launch(it)
        }
        viewModel.authSuccessFlow.launchAndCollectIn(lifecycleOwner.value) {

            val user = viewModel.getUser(viewModel.config.login)
            val userJson = Gson().toJson(user)
            navController.navigate(Screen.Profile.route+"/"+ Uri.encode(userJson))
            onClose()
        }
        viewModel.remoteUserGithubInfoFlow.launchAndCollectIn(lifecycleOwner.value) {
            val a = it

        }
    }
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.onBackground)
            .fillMaxWidth()
            .padding(top = 10.dp)
            .fillMaxHeight(0.95f),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 15.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                LabelText(
                    text = "Authentication",
                    modifier = Modifier)
            }
            Box(
                contentAlignment = Alignment.BottomEnd,
                modifier = Modifier.fillMaxWidth()
            )
            {
                CloseButton( onClose)
            }
        }
        CustomText(
            text = "in the discount service with free coupons\n " +
                    "and promo codes with 100% discount",
            fontSize = 13.sp
        )
        Divider( thickness = 1.dp, color = TextFieldLabelColor)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 15.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomTextField(value = "", label = "email")

            Button(
                onClick = {

                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = YellowGlimon
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp)
                //modifier = Modifier.clip(RoundedCornerShape(1.dp))
            ) {
                Text(
                    text = "Login",
                    color = Color.Black,
                    modifier = Modifier.padding(10.dp)
                )
            }
            SocialNetworkIcons(viewModel = viewModel)
            TextWithLink(textLink = "Sign Up", textBefore ="New to Glimon?" ) {

            }
            Column(   verticalArrangement = Arrangement.spacedBy(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {

                CustomText(text = "By continuing, you agree to Glimon's", textColor = TextFieldLabelColor)
                TextWithLink(textLink = "Conditions of Use " , textAfter = "and") {

                }
                TextWithLink(textLink = "Privacy Notice") {

                }
            }

        }
    }
}

@Composable
fun SocialNetworkIcons(viewModel: LoginViewModel) {
    CustomText(text = "Or by social media")
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        SocialNetworkIcon(color = OrangeOdnoklassniki, idIcon = R.drawable.ok_logo) {

        }
        SocialNetworkIcon(color = Color.White, idIcon = R.drawable.github_logo)
        {
            viewModel.config = GithubAuthConfig
            viewModel.openLoginPage(GithubAuthConfig.login)

        }
        SocialNetworkIcon(color = BlueVK, idIcon = R.drawable.vk_logo)
        {
            viewModel.config = MailAuthConfig
            viewModel.openLoginPage(MailAuthConfig.login)
        }
    }
}

fun handleAuthResponseIntent(intent: Intent, viewModel: LoginViewModel) {
    // пытаемся получить ошибку из ответа. null - если все ок
    val exception = AuthorizationException.fromIntent(intent)
    // пытаемся получить запрос для обмена кода на токен, null - если произошла ошибка
    val tokenExchangeRequest = AuthorizationResponse.fromIntent(intent)
        ?.createTokenExchangeRequest()
    when {
        // авторизация завершались ошибкой
        exception != null -> viewModel.onAuthCodeFailed()
        // авторизация прошла успешно, меняем код на токен
        tokenExchangeRequest != null ->
            viewModel.onAuthCodeReceived(tokenExchangeRequest)
    }
}

@Composable
fun CloseButton( onClose: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .shadow(8.dp, shape = CircleShape)
            .size(25.dp)
            .clip(CircleShape)
            .clickable {
                onClose()
            }
            .background(Color.Gray)
    ) {
        Icon(
            Icons.Filled.Close, contentDescription = "Hide Authorization Sheet",
            tint = MaterialTheme.colorScheme.background,
            modifier = Modifier.size(24.dp)
        )
    }
}

