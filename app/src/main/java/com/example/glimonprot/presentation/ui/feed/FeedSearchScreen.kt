package example.glimonprot.presentation.ui.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.LazyPagingItems
import com.example.glimonprot.di.viewModelFactory.ViewModelFactory
import com.example.glimonprot.R
import com.example.glimonprot.domain.model.FeedItem
import com.example.glimonprot.presentation.components.CustomText
import com.example.glimonprot.presentation.components.LabelText
import com.example.glimonprot.presentation.theme.TextFieldContainerColor
import com.example.glimonprot.presentation.theme.TextFieldLabelColor
import com.example.glimonprot.presentation.ui.feed.FeedViewModel

@Composable
fun FeedSearchScreen(
    factory: ViewModelFactory,
    feedItems: LazyPagingItems<FeedItem>,
    text: String
) {
    val viewModel: FeedViewModel = viewModel(factory = factory)
    if (text != "null")
        viewModel.onSearchTextChange(text)
    val searchText by viewModel.searchText.collectAsState()
    viewModel.setFeedItems(feedItems)
    val photosList by viewModel.photos.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.onBackground)
            .fillMaxSize()
            .padding(horizontal = 15.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
                .clip(shape = RoundedCornerShape(15.dp))
                .background(TextFieldContainerColor),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isSearching) {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                        .size(30.dp)
                ) {
//                    CircularProgressIndicator(
//                        modifier = Modifier.align(Alignment.Center),
//                        trackColor = Color.White
//                    )
                }
            } else {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowLeft,
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                        .size(30.dp),
                    contentDescription = "BackButton",
                    tint = TextFieldLabelColor
                )
            }
            TextField(
                modifier = Modifier.weight(1f),
                value = searchText,
                onValueChange = viewModel::onSearchTextChange,
                colors = TextFieldDefaults.colors(
                    unfocusedTextColor = MaterialTheme.colorScheme.onSecondary,
                    focusedTextColor = MaterialTheme.colorScheme.onSecondary,
                    focusedContainerColor = TextFieldContainerColor,
                    unfocusedContainerColor = TextFieldContainerColor,
                    disabledIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    unfocusedLabelColor = TextFieldLabelColor,

                    ),
                label = { Text("Search for anything...") }
            )
            Icon(
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .size(26.dp),
                painter = painterResource(id = R.drawable.baseline_mic_24),
                contentDescription = null,
                tint = TextFieldLabelColor
            )
        }
        Divider(thickness = 2.dp, color = Color(0xFF1A1919))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(photosList) { feedItem ->
                LabelText(text = feedItem.companyName ?: "name")
                CustomText(text = feedItem.promotionName ?: "name")
                CustomText(text = feedItem.description  ?: "Description")
                CustomText(text = feedItem.location ?: "Location")
                CustomText(
                    text = (feedItem.companyName ?: "") +
                            (feedItem.promotionName ?: "") +
                            (feedItem.description  ?: "") +
                            (feedItem.location ?: ""),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                )
                Divider(thickness = 1.dp, color = TextFieldLabelColor)
            }
        }
    }

}