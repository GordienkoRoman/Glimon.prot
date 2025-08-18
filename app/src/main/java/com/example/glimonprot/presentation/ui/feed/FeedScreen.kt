package com.example.glimonprot.presentation.ui.feed

import android.app.Activity
import android.content.Intent
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.LazyPagingItems
import com.example.glimonprot.R
import kotlinx.coroutines.flow.StateFlow
import com.example.glimonprot.di.viewModelFactory.ViewModelFactory
import com.example.glimonprot.domain.model.FeedItem
import com.example.glimonprot.presentation.bottomSheets.ChangeLocationBottomSheet
import com.example.glimonprot.presentation.components.CustomButton
import com.example.glimonprot.presentation.components.SelectButton
import com.example.glimonprot.presentation.components.TwoRowsTopAppBar
import com.example.glimonprot.presentation.theme.TextFieldContainerColor
import com.example.glimonprot.presentation.theme.TextFieldLabelColor
import example.glimonprot.presentation.ui.feed.FeedItemComponent
import stud.gilmon.data.local.entities.UsersEntity
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(
    feedItems: LazyPagingItems<FeedItem>,
    factory: ViewModelFactory,
    user: StateFlow<UsersEntity>,
    onSearchClick: (String) -> Unit,
    onItemClick: (Int) -> Unit
) {
    val location = rememberSaveable { mutableStateOf("") }

    val viewModel: FeedViewModel = viewModel(factory = factory)
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val scrollBehavior2 = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    SideEffect {
      //  viewModel.setPhotos(photos)
    }
    val showChooseLocationBottomSheet = rememberSaveable { mutableStateOf(false) }
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior2.nestedScrollConnection),
        topBar = {
            TwoRowsTopAppBar(
                title = {
                    SearchBar(userlocation = "", onSearchClick)
                },
                pinnedHeight = 60.dp,
                maxHeight = 250.dp,
                scrollBehavior = scrollBehavior2
            )
        }
    ) {
        Scaffold(
            modifier = Modifier
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .fillMaxSize()
                .padding(it),
            topBar = {
                TwoRowsTopAppBar(
                    title = {},
                    pinnedHeight = 0.dp,
                    maxHeight = 1.dp,
                    scrollBehavior = scrollBehavior
                )
            }
        ) {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(bottom = it.calculateBottomPadding()),
                contentPadding = PaddingValues(
                    bottom = 75.dp,
                    top = 30.dp,
                    start = 15.dp,
                    end = 15.dp
                ),
                /*   .windowInsetsTopHeight(WindowInsets.safeDrawing)*/
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                items(count = feedItems.itemCount) { index ->
                    val item = feedItems[index]
                    FeedItemComponent(
                        item!!,
                        onItemClick = onItemClick,
                        index = index
                    )
                }
            }
        }

    }
    ChangeLocationBottomSheet(
        showModalBottomSheet = showChooseLocationBottomSheet,
        option = location
    ) {
        showChooseLocationBottomSheet.value = false
    }
}


@Composable
fun SearchBar(userlocation: String, onSearckClick: (String) -> Unit) {

    val searchText = rememberSaveable {
        mutableStateOf("")
    }
    val context = LocalContext.current
    val showChooseLocationBottomSheet = rememberSaveable { mutableStateOf(false) }
    val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
    intent.putExtra(
        RecognizerIntent.EXTRA_LANGUAGE_MODEL,
        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
    )
    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
    val recognizeVoiceLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(), onResult = {
            if (it.resultCode == Activity.RESULT_OK) {
                val text = it.data?.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS
                ) as ArrayList<String>
                onSearckClick(text[0])
            }
        })
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.onBackground)
            .padding(15.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SelectButton(
            labelText = "Show promotions in",
            text = "location.value",
            icon = Icons.Filled.LocationOn,
            containerColor = TextFieldContainerColor
        )
        {
            showChooseLocationBottomSheet.value = !showChooseLocationBottomSheet.value
        }
        Button(
            onClick = { onSearckClick("null") },
            colors = ButtonDefaults.buttonColors(
                containerColor = TextFieldContainerColor
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Icon(
                modifier = Modifier.size(26.dp),
                imageVector = Icons.Filled.Search,
                contentDescription = null,
                tint = TextFieldLabelColor
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                modifier = Modifier.weight(1f),
                text = "text",
                color = TextFieldLabelColor,
            )

            Icon(
                modifier = Modifier
                    .size(26.dp)
                    .clickable {
                        if (!SpeechRecognizer.isRecognitionAvailable(context)) {
                            Toast
                                .makeText(context, "Mic is off", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            recognizeVoiceLauncher.launch(intent)
                        }
                    },
                painter = painterResource(id = R.drawable.baseline_mic_24),
                contentDescription = null,
                tint = TextFieldLabelColor
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { },
                modifier = Modifier.size(45.dp),
                shape = RoundedCornerShape(10.dp),
            ) {
                Icon(
                    modifier = Modifier.size(10.dp),
                    imageVector = Icons.Filled.Search,
                    contentDescription = null,
                    tint = Color.Red
                )
            }
            Spacer(
                modifier = Modifier
                    .width(1.dp)
                    .background(TextFieldContainerColor)
                    .height(30.dp)
                    .padding(horizontal = 15.dp)
            )
            LazyRow(horizontalArrangement = spacedBy(10.dp)) {
                items(10) {
                    CustomButton(
                        text = "button$it",
                        containerColor = TextFieldContainerColor
                    ) { }
                }
            }
        }
    }
}