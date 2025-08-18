package com.example.glimonprot.presentation.ui.feed

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.compose.LazyPagingItems
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import com.example.glimonprot.domain.model.FeedItem
import com.example.glimonprot.domain.repository.GlimonRepository

@OptIn(FlowPreview::class)
class FeedViewModel @Inject constructor(
    context: Context,
    //TODO()
    private val dataStoreRepository: GlimonRepository,
    private val roomRepository: GlimonRepository,
) : ViewModel() {
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _feedItems = MutableStateFlow(listOf(FeedItem(0,"","","","","",)))
    fun setFeedItems(feedItems: LazyPagingItems<FeedItem>,)
    {
        _feedItems.value = feedItems.itemSnapshotList.items
    }
    val photos = searchText
        .debounce(500L)
        .onEach { _isSearching.update { true } }
        .combine(_feedItems) { text, photos ->
            if(text.isBlank()) {
                photos
            } else {
                delay(2000L)
                photos.filter {
                    it.doesMatchSearchQuery(text)
                }
            }
        }
        .onEach { _isSearching.update { false } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _feedItems.value
        )

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

}
