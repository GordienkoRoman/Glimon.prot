package com.example.glimonprot.presentation.ui.profile.reviews

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.glimonprot.di.viewModelFactory.ViewModelFactory
import com.example.glimonprot.R
import com.example.glimonprot.domain.model.ReviewItem
import com.example.glimonprot.presentation.components.CustomBottomSheetContainer
import com.example.glimonprot.presentation.components.CustomList
import com.example.glimonprot.presentation.components.CustomText
import com.example.glimonprot.presentation.components.LabelText
import com.example.glimonprot.presentation.components.SelectButton
import com.example.glimonprot.presentation.theme.TextFieldLabelColor
import com.example.glimonprot.presentation.ui.profile.TOP_NAVIGATION_BAR_HEICHT

@Composable
fun ReviewsProfile(userId: String, lazyListState: LazyListState, factory: ViewModelFactory) {
    val viewModel: ReviewsViewModel = viewModel(factory = factory)
    val reviewsStatus = rememberSaveable { mutableStateOf("All") }
    val sortType = rememberSaveable { mutableStateOf("By new") }
    val screenState = viewModel.screenState.collectAsState(ReviewsScreenState.Initial)
    val showReviewsStatusBottomSheet = rememberSaveable { mutableStateOf(false) }
    val showSortTypeBottomSheet = rememberSaveable { mutableStateOf(false) }
    val currentState = screenState.value
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(
                top = TOP_NAVIGATION_BAR_HEICHT,
                start = 15.dp,
                end = 15.dp,
                bottom = 75.dp
            )
            .navigationBarsPadding(),
        state = lazyListState,
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        item {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(15.dp)
            )
            SelectButton("Review status", reviewsStatus.value)
            {
                showReviewsStatusBottomSheet.value = !showReviewsStatusBottomSheet.value
            }
        }
        item {
            SelectButton("How to sort?", sortType.value)
            {
                showSortTypeBottomSheet.value = !showSortTypeBottomSheet.value
            }
        }

        if(currentState is ReviewsScreenState.Reviews)
        {
            if(currentState.reviews.isEmpty())
            {
                item {
                    EmptyList()
                }
            }
            items(
                currentState.reviews.size
            )
            {
                val reviewItem = currentState.reviews[it]
                ReviewItem(reviewItem,userId,viewModel)

            }
        }
        else item {
            EmptyList()
        }
    }
    SortTypeBottomSheet(showSortTypeBottomSheet, sortType)
    ReviewsStatusBottomSheet(showReviewsStatusBottomSheet, reviewsStatus)
}

@Composable
fun ReviewsStatusBottomSheet(
    showModalBottomSheet: MutableState<Boolean>,
    option: MutableState<String>
) {
    if (showModalBottomSheet.value)
        CustomBottomSheetContainer(
            onDismissRequest = { showModalBottomSheet.value = false }) {
            CustomList(listOf("All", "Positive", "Negative"), option, showModalBottomSheet)
        }
}


@Composable
fun SortTypeBottomSheet(showModalBottomSheet: MutableState<Boolean>, option: MutableState<String>) {
    if (showModalBottomSheet.value)
        CustomBottomSheetContainer(
            onDismissRequest = { showModalBottomSheet.value = false }) {
            CustomList(listOf("By new", "By old", "By usefulness"), option, showModalBottomSheet)
        }
}

@Composable
fun EmptyList(){
    Column(
        Modifier.height(200.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.baseline_people_24),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSecondary,
            modifier = Modifier.size(50.dp)
        )
        Text(
            text = "Empty list",
            fontSize = 30.sp,
            color = Color.White
        )
    }
}
@Composable
fun ReviewItem(reviewItem: ReviewItem,userId:String,viewModel: ReviewsViewModel){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { },
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier
                .clickable { }
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.onBackground)
                .padding(horizontal = 15.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(model = reviewItem.feedItem.imgUrl,
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape),
                    contentDescription = "",
                    contentScale = ContentScale.Crop)
                LabelText(text = reviewItem.feedItem.companyName)
            }
            CustomText(text = reviewItem.review)
            Row{
                Icon(imageVector = Icons.Filled.ThumbUp, contentDescription = "", tint = MaterialTheme.colorScheme.tertiary)
                CustomText(text = "Useful?", textColor = TextFieldLabelColor)
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "",
                    tint = TextFieldLabelColor,
                    modifier = Modifier.clickable { viewModel.deleteReview(reviewItem, userId) }
                )
            }
        }
    }
}