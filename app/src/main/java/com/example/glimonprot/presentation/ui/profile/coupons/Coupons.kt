package com.example.glimonprot.presentation.ui.profile.coupons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
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
import coil.compose.SubcomposeAsyncImage
import com.example.glimonprot.di.viewModelFactory.ViewModelFactory
import com.example.glimonprot.R
import com.example.glimonprot.domain.model.FeedItem
import com.example.glimonprot.presentation.components.CustomBottomSheetContainer
import com.example.glimonprot.presentation.components.CustomList
import com.example.glimonprot.presentation.components.CustomText
import com.example.glimonprot.presentation.components.LabelText
import com.example.glimonprot.presentation.components.SelectButton
import com.example.glimonprot.presentation.theme.TextFieldLabelColor
import com.example.glimonprot.presentation.ui.profile.TOP_NAVIGATION_BAR_HEICHT


@Composable
fun CouponsProfile(userId: String, lazyListState: LazyListState, factory: ViewModelFactory) {

    val viewModel: CouponsViewModel = viewModel(factory = factory)
    val couponStatus = rememberSaveable { mutableStateOf("All") }
    val sortType = rememberSaveable { mutableStateOf("By new") }
    val screenState = viewModel.screenState.collectAsState(CouponsScreenState.Initial)
    val showCouponStatusBottomSheet = rememberSaveable { mutableStateOf(false) }
    val showSortTypeBottomSheet = rememberSaveable { mutableStateOf(false) }
    CouponsStatusBottomSheet(showCouponStatusBottomSheet, couponStatus)
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
            SelectButton("Coupon status", couponStatus.value)
            {
                showCouponStatusBottomSheet.value = !showCouponStatusBottomSheet.value
            }
        }
        item {
            SelectButton("How to sort?", sortType.value)
            {
                showSortTypeBottomSheet.value = !showSortTypeBottomSheet.value
            }
        }

        if (currentState is CouponsScreenState.Coupons) {
            items(
                currentState.coupons.size
            )
            {
                val feedItem = currentState.coupons[it]
                CouponItem(feedItem = feedItem,userId,viewModel)

            }
        } else {
            item {
                Column(
                    Modifier
                        .clip(shape = RoundedCornerShape(20.dp))
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.onBackground),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.baseline_people_24),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(50.dp)
                    )
                    Text(
                        text = "Empty list",
                        fontSize = 30.sp,
                        color = Color.White
                    )
                }
            }
        }

    }
    SortTypeBottomSheet(showSortTypeBottomSheet, sortType)

}

@Composable
fun CouponItem(feedItem: FeedItem,userId: String,viewModel: CouponsViewModel) {
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
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
        ) {
            Row {
                LabelText(text = feedItem.companyName, modifier = Modifier.weight(1f))
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "",
                    tint = TextFieldLabelColor,
                    modifier = Modifier.clickable { viewModel.deleteCoupon(feedItem, userId)}
                )
            }
            CustomText(text = feedItem.promotionName)
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
                       // CircularProgressIndicator()
                    }
                )
            }
            CustomText(text = feedItem.description)
            CustomText(text = feedItem.location)
        }
        HorizontalDivider(thickness = 1.dp, color = Color.DarkGray)

        // FeedItemBottom(photo)
    }
}

@Composable
fun CouponsStatusBottomSheet(
    showModalBottomSheet: MutableState<Boolean>,
    option: MutableState<String>
) {
    if (showModalBottomSheet.value)
        CustomBottomSheetContainer(
            onDismissRequest = { showModalBottomSheet.value = false }) {
            CustomList(listOf("All", "Current", "Expired"), option, showModalBottomSheet)
        }
}

@Composable
fun SortTypeBottomSheet(showModalBottomSheet: MutableState<Boolean>, option: MutableState<String>) {
    if (showModalBottomSheet.value)
        CustomBottomSheetContainer(
            onDismissRequest = { showModalBottomSheet.value = false }) {
            CustomList(
                listOf("By new", "By old", "By amount of discount"),
                option,
                showModalBottomSheet
            )
        }
}


