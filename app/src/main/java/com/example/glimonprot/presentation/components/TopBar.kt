package com.example.glimonprot.presentation.components

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.unit.Dp
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TwoRowsTopAppBar(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    smallTitle: @Composable () -> Unit={},
    maxHeight: Dp,
    pinnedHeight: Dp,
    scrollBehavior: TopAppBarScrollBehavior?
) {
    if (maxHeight <= pinnedHeight) {
        throw IllegalArgumentException(
            "A TwoRowsTopAppBar max height should be greater than its pinned height"
        )
    }
    val pinnedHeightPx: Float
    val maxHeightPx: Float
    val titleBottomPaddingPx: Int
    LocalDensity.current.run {
        pinnedHeightPx = pinnedHeight.toPx()
        maxHeightPx = maxHeight.toPx()
    }

    // Sets the app bar's height offset limit to hide just the bottom title area and keep top title
    // visible when collapsed.
    SideEffect {
        if (scrollBehavior?.state?.heightOffsetLimit != pinnedHeightPx - maxHeightPx) {
            scrollBehavior?.state?.heightOffsetLimit = pinnedHeightPx - maxHeightPx
        }
    }

    // Obtain the container Color from the TopAppBarColors using the `collapsedFraction`, as the
    // bottom part of this TwoRowsTopAppBar changes color at the same rate the app bar expands or
    // collapse.
    // This will potentially animate or interpolate a transition between the container color and the
    // container's scrolled color according to the app bar's scroll state.
    val colorTransitionFraction = scrollBehavior?.state?.collapsedFraction ?: 0f

    // Wrap the given actions in a Row.

    // Hide the top row title semantics when its alpha value goes below 0.5 threshold.
    // Hide the bottom row title semantics when the top title semantics are active.
    val hideTopRowSemantics = colorTransitionFraction < 0.5f
    val hideBottomRowSemantics = !hideTopRowSemantics

    // Set up support for resizing the top app bar when vertically dragging the bar itself.
    val appBarDragModifier = if (scrollBehavior != null && !scrollBehavior.isPinned) {
        Modifier.draggable(
            orientation = Orientation.Vertical,
            state = rememberDraggableState { delta ->
                scrollBehavior.state.heightOffset = scrollBehavior.state.heightOffset + delta
            },
            onDragStopped = {
            }
        )
    } else {
        Modifier
    }

    Surface(modifier = modifier.then(appBarDragModifier), color = MaterialTheme.colorScheme.background) {
        Column {
//            TopAppBarLayout(
//                modifier = Modifier
//                    // clip after padding so we don't show the title over the inset area
//                    .clipToBounds(),
//                heightPx = pinnedHeightPx,
//                titleContentColor =  Color.Red,
//                actionIconContentColor =
//                Color.Red,
//                title = smallTitle,
//                titleVerticalArrangement = Arrangement.Center,
//                titleHorizontalArrangement = Arrangement.Start,
//                hideTitleSemantics = hideTopRowSemantics,
//            )
            TopAppBarLayout(
                modifier = Modifier
                    // only apply the horizontal sides of the window insets padding, since the top
                    // padding will always be applied by the layout above
                    .clipToBounds(),
                heightPx = maxHeightPx - pinnedHeightPx + (scrollBehavior?.state?.heightOffset
                    ?: 0f),
                titleContentColor =  Color.Red,
                title = title,
                titleVerticalArrangement = Arrangement.Bottom,
                titleHorizontalArrangement = Arrangement.Start,
                hideTitleSemantics = hideBottomRowSemantics,
            )
        }
    }
}
@Composable
private fun TopAppBarLayout(
    modifier: Modifier,
    heightPx: Float,
    titleContentColor: Color,
    title: @Composable () -> Unit,
    titleVerticalArrangement: Arrangement.Vertical,
    titleHorizontalArrangement: Arrangement.Horizontal,
    hideTitleSemantics: Boolean,
) {
    Layout(
        {
            Box(
                Modifier
                    .layoutId("title")
                    .then(if (hideTitleSemantics) Modifier.clearAndSetSemantics { } else Modifier)
            ) {
                    CompositionLocalProvider(
                        LocalContentColor provides titleContentColor,
                        content = title
                    )
            }
        },
        modifier = modifier
    ) { measurables, constraints ->

        val maxTitleWidth = constraints.maxWidth
        val titlePlaceable =
            measurables.first { it.layoutId == "title" }
                .measure(constraints.copy(minWidth = 0, maxWidth = maxTitleWidth))

        // Locate the title's baseline.

        val layoutHeight = heightPx.roundToInt()

        layout(constraints.maxWidth, layoutHeight) {

            // Title
            titlePlaceable.placeRelative(
                x = when (titleHorizontalArrangement) {
                    Arrangement.Center -> (constraints.maxWidth - titlePlaceable.width) / 2
                    Arrangement.End ->
                        constraints.maxWidth - titlePlaceable.width
                    // Arrangement.Start.
                    // An TopAppBarTitleInset will make sure the title is offset in case the
                    // navigation icon is missing.
                    else -> 0
                },
                y = when (titleVerticalArrangement) {
                    Arrangement.Center -> (layoutHeight - titlePlaceable.height) / 2
                    // Apply bottom padding from the title's baseline only when the Arrangement is
                    // "Bottom".
                    // Arrangement.Top
                    else -> 0
                }
            )
            // Action icons
        }
    }
}