package example.glimonprot.presentation.ui.feed.FeedItemScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.glimonprot.R
import kotlinx.coroutines.flow.StateFlow
import com.example.glimonprot.di.viewModelFactory.ViewModelFactory
import com.example.glimonprot.domain.model.FeedItem
import com.example.glimonprot.presentation.bottomSheets.WriteReviewBottomSheet
import com.example.glimonprot.presentation.components.CustomBottomSheetContainer
import com.example.glimonprot.presentation.components.CustomButton
import com.example.glimonprot.presentation.components.CustomText
import com.example.glimonprot.presentation.components.LabelText
import com.example.glimonprot.presentation.theme.TextFieldContainerColor
import com.example.glimonprot.presentation.theme.TextFieldLabelColor
import com.example.glimonprot.presentation.theme.YellowGlimon
import com.example.glimonprot.presentation.ui.feed.feedItemScreen.FeedItemViewModel
import stud.gilmon.data.local.entities.UsersEntity


@Composable
fun FeedItemScreen(
    user: StateFlow<UsersEntity>,
    factory: ViewModelFactory,
    feedItem: FeedItem) {
    val showFeedItemBottomSheet = rememberSaveable {
        mutableStateOf(true) }
    val configuration = LocalConfiguration.current
    val viewModel: FeedItemViewModel = viewModel(factory = factory)
    val screenHeight = configuration.screenHeightDp.dp
    val height = remember { mutableStateOf(screenHeight - 100.dp) }

//    val feedItem = FeedItem(
//        companyName = "name",
//        promotionName =  "name",
//        description = "Description",
//        location =  "Location",
//        imgUrl = feedItem.imgUrl
//    )
    val feedItem = FeedItem(
        companyName = feedItem.companyName ?: "name",
        promotionName = feedItem.promotionName ?: "name",
        description = feedItem.location ?: "Description",
        location =feedItem.description?: "Location",
        imgUrl = feedItem.imgUrl
    )
    Surface {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            modifier = Modifier
                .fillMaxSize(),
            contentDescription = "Map image"
        )
        CustomButton(text = "Show promotion")
        {
            showFeedItemBottomSheet.value = !showFeedItemBottomSheet.value
        }
    }

    if (showFeedItemBottomSheet.value)
        FeedItemBottomSheet(
            showModalBottomSheet = showFeedItemBottomSheet,
            user,
            feedItem,
            height,
            viewModel
        ) {
            showFeedItemBottomSheet.value = !showFeedItemBottomSheet.value
        }
    else {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {
            CustomButton(
                text = "Show promotion",
                modifier = Modifier.padding(horizontal = 40.dp),
                containerColor = MaterialTheme.colorScheme.background
            )
            {
                showFeedItemBottomSheet.value = true
            }
        }

    }
}

@Composable
fun FeedItemBottomSheet(
    showModalBottomSheet: MutableState<Boolean>,
    user: StateFlow<UsersEntity>,
    feedItem: FeedItem,
    height: MutableState<Dp>,
    viewModel: FeedItemViewModel,
    onDismissRequest: () -> Unit
) {
    val showWriteReviewsBottomSheet = rememberSaveable { mutableStateOf(false) }
    if (showModalBottomSheet.value)
        CustomBottomSheetContainer(
            onDismissRequest = onDismissRequest
        ) {
            LazyColumn(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.onBackground)
                    .height(height.value)
                    .padding(horizontal = 15.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp),
            ) {
                item {
                    LabelText(text = feedItem.companyName)

                }
                item {
                    LinksRow()

                }
                item {
                    CustomText(text = feedItem.description)

                }
                item {
                    AsyncImage(
                        model = feedItem.imgUrl,
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(shape = RoundedCornerShape(15.dp)),
                        contentScale = ContentScale.Crop,
                    )
                }

                item {
                    CustomButton(
                        text = "Get Promotion",
                        textColor = Color.Black,
                        modifier = Modifier.align(Alignment.End),
                        containerColor = YellowGlimon
                    )
                    {
                        viewModel.insertCoupon(feedItem, user.value.userId)
                    }
                }
                item {
                    FeedScreenReviews(
                        feedItem = feedItem,
                        user = user,
                        showModalBottomSheet = showWriteReviewsBottomSheet,
                        viewModel = viewModel
                    )
                }

            }
        }
}

@Composable
fun FeedScreenReviews(
    reviewsCount: Int = 0,
    feedItem: FeedItem,
    user: StateFlow<UsersEntity>,
    showModalBottomSheet: MutableState<Boolean>,
    viewModel: FeedItemViewModel
) {
    val review = remember {
        mutableStateOf("")
    }
    if (reviewsCount == 0) {
        LabelText("No reviews")
    } else {
        LabelText(text = "Reviews $reviewsCount")

    }
    CustomText(text = "Tell us about this promotion...", textColor = TextFieldLabelColor)
    CustomButton(
        text = "Write a review",
        containerColor = YellowGlimon,
        modifier = Modifier.fillMaxWidth()
    )
    {
        showModalBottomSheet.value = !showModalBottomSheet.value
    }
    WriteReviewBottomSheet(showModalBottomSheet = showModalBottomSheet, option = review) {
        if (it != "") {
            viewModel.insertReview(feedItem, user.value.userId, it)
        }
        showModalBottomSheet.value = !showModalBottomSheet.value
    }

}

@Preview
@Composable
fun LinksRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ButtonWithIcon(imageVector = Icons.Filled.Call)
        {

        }
        ButtonWithIcon(painter = painterResource(id = R.drawable.ic_launcher_foreground))
        {

        }
        ButtonWithIcon(imageVector = Icons.Filled.Place)
        {

        }
        ButtonWithIcon(imageVector = Icons.Filled.Notifications)
        {

        }

    }
}

@Composable
fun ButtonWithIcon(
    modifier: Modifier = Modifier,
    imageVector: ImageVector? = null,
    painter: Painter? = null,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = TextFieldContainerColor
        ),
        contentPadding = PaddingValues(0.dp),
        shape = RoundedCornerShape(5.dp),
        modifier = modifier
            .width(60.dp)
    ) {
        if (imageVector != null)
            Icon(
                imageVector = imageVector,
                tint = TextFieldLabelColor,
                contentDescription = "Call"
            )
        else
            if (painter != null)
                Icon(
                    painter = painter,
                    tint = TextFieldLabelColor,
                    contentDescription = "Call"
                )
    }
}
