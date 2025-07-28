package stud.gilmon.presentation.ui.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import stud.gilmon.R
import stud.gilmon.data.model.FeedItem
import stud.gilmon.data.remote.UnsplashDto
import stud.gilmon.presentation.components.CustomText
import stud.gilmon.presentation.components.IconWithText
import stud.gilmon.presentation.components.LabelText

@Composable
fun FeedItemComponent(
    feedItem: FeedItem,
    onItemClick: (Int) -> Unit = {},
    index: Int=0
) {
//    val feedItem = remember {
//        mutableStateOf(FeedItem(0,"","","","",""
//        ))
//    }
//    SideEffect {
//        feedItem.value=FeedItem(
//            companyName = photo.user.name ?: "name",
//            promotionName = photo.user.name ?: "name",
//            description = photo.description ?: photo.user.bio ?: "Description",
//            location = photo.location.name ?: photo.user.location ?: "Location",
//            imgUrl = photo.urls?.raw.toString()
//        )
//    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(index) },
        shape = RoundedCornerShape(20.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            SubcomposeAsyncImage(
                model = feedItem.imgUrl,
                contentDescription = "",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop,
                loading = {
                //    CircularProgressIndicator()
                }
            )
        }
        Column(
            modifier = Modifier
                .clickable { }
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.onBackground)
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
        ) {
            LabelText(text = feedItem.companyName)
            CustomText(text = feedItem.promotionName)
            CustomText(text = feedItem.description)
            CustomText(text = feedItem.location)
        }
        Divider(thickness = 1.dp, color = Color.DarkGray)

        FeedItemBottom(feedItem)
    }
}



@Composable
fun FeedItemBottom(feedItem: FeedItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.onBackground)
            .padding(horizontal = 15.dp, vertical = 20.dp)
    ) {
        IconWithText(
            painter = painterResource(R.drawable.baseline_shopping_cart_24),
            text = (feedItem.downloads).toString()
        )
        Spacer(modifier = Modifier.width(15.dp))
        IconWithText(
            painter = painterResource(R.drawable.baseline_message_24),
            text = (feedItem.likes).toString()
        )

    }
}