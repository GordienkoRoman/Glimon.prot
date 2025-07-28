package stud.gilmon.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.map
import kotlin.math.roundToInt

@Composable
fun RowScope.CustomNavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: @Composable (() -> Unit)? = null,
    alwaysShowLabel: Boolean = true,
    indicatorColor:Color,
    textColor:Color,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {

    var itemWidth by remember { mutableStateOf(0) }
    val styledLabel: @Composable (() -> Unit)? = label?.let {
        @Composable {
            val style = MaterialTheme.typography.bodyMedium
            val textColor = textColor
            CompositionLocalProvider(LocalContentColor provides textColor) {
                ProvideTextStyle(style, content = label)
            }
        }
    }
    Box(
        modifier
            .selectable(
                selected = selected,
                onClick = onClick,
                enabled = enabled,
                role = Role.Tab,
                interactionSource = interactionSource,
                indication = null,
            )
            .weight(1f)
            .onSizeChanged {
                itemWidth = it.width
            },
        contentAlignment = Alignment.Center
    ) {
        val animationProgress: Float by animateFloatAsState(
            targetValue = if (selected) 1f else 0f,
            animationSpec = tween(ItemAnimationDurationMillis),
            label = ""
        )

        // The entire item is selectable, but only the indicator pill shows the ripple. To achieve
        // this, we re-map the coordinates of the item's InteractionSource into the coordinates of
        // the indicator.
        val deltaOffset: Offset
        with(LocalDensity.current) {
            val indicatorWidth = NavigationBarTokens.ActiveIndicatorWidth.roundToPx()
            deltaOffset = Offset(
                (itemWidth - indicatorWidth).toFloat() / 2,
                IndicatorVerticalOffset.toPx()
            )
        }
        val offsetInteractionSource = remember(interactionSource, deltaOffset) {
            MappedInteractionSource(interactionSource, deltaOffset)
        }

        // The indicator has a width-expansion animation which interferes with the timing of the
        // ripple, which is why they are separate composables
        val indicatorRipple = @Composable {
            Box(
                Modifier
                    .layoutId(IndicatorRippleLayoutIdTag)
                    .clip(NavigationBarTokens.ActiveIndicatorShape.toShape())
                    .indication(offsetInteractionSource, rememberRipple())
            )
        }
        val indicator = @Composable {
            Box(
                Modifier
                    .layoutId(IndicatorLayoutIdTag)
                    .background(
                        color = indicatorColor,
                        shape = NavigationBarTokens.ActiveIndicatorShape.toShape(),
                    )
            )
        }

        NavigationBarItemBaselineLayout(
            indicatorRipple = indicatorRipple,
            indicator = indicator,
            label = styledLabel,
            alwaysShowLabel = alwaysShowLabel,
            animationProgress = animationProgress
        )
    }
}
@Composable
fun ShapeKeyTokens.toShape(): Shape {
    return MaterialTheme.shapes.fromToken(this)
}
fun Shapes.fromToken(value: ShapeKeyTokens): Shape {
    return extraLarge

}
class MappedInteractionSource(
    underlyingInteractionSource: InteractionSource,
    private val delta: Offset
) : InteractionSource {
    private val mappedPresses =
        mutableMapOf<PressInteraction.Press, PressInteraction.Press>()

    override val interactions = underlyingInteractionSource.interactions.map { interaction ->
        when (interaction) {
            is PressInteraction.Press -> {
                val mappedPress = mapPress(interaction)
                mappedPresses[interaction] = mappedPress
                mappedPress
            }
            is PressInteraction.Cancel -> {
                val mappedPress = mappedPresses.remove(interaction.press)
                if (mappedPress == null) {
                    interaction
                } else {
                    PressInteraction.Cancel(mappedPress)
                }
            }
            is PressInteraction.Release -> {
                val mappedPress = mappedPresses.remove(interaction.press)
                if (mappedPress == null) {
                    interaction
                } else {
                    PressInteraction.Release(mappedPress)
                }
            }
            else -> interaction
        }
    }

    private fun mapPress(press: PressInteraction.Press): PressInteraction.Press =
        PressInteraction.Press(press.pressPosition - delta)
}


@Composable
private fun NavigationBarItemBaselineLayout(
    indicatorRipple: @Composable () -> Unit,
    indicator: @Composable () -> Unit,
    label: @Composable (() -> Unit)?,
    alwaysShowLabel: Boolean,
    animationProgress: Float,
) {
    Layout({
        indicatorRipple()
        if (animationProgress > 0) {
            indicator()
        }
        if (label != null) {
            Box(
                Modifier
                    .layoutId(LabelLayoutIdTag)
                    .alpha(if (alwaysShowLabel) 1f else animationProgress)
                    .padding(horizontal = NavigationBarItemHorizontalPadding / 2)
            ) { label() }
        }
    }) { measurables, constraints ->
        val iconPlaceable =
            measurables.first { it.layoutId == IconLayoutIdTag }.measure(constraints)

        val totalIndicatorWidth = iconPlaceable.width + (IndicatorHorizontalPadding * 2).roundToPx()
        val animatedIndicatorWidth = (totalIndicatorWidth * animationProgress).roundToInt()
        val indicatorHeight = iconPlaceable.height + (IndicatorVerticalPadding * 2).roundToPx()
        val indicatorRipplePlaceable =
            measurables
                .first { it.layoutId == IndicatorRippleLayoutIdTag }
                .measure(
                    Constraints.fixed(
                        width = totalIndicatorWidth,
                        height = indicatorHeight
                    )
                )
        val indicatorPlaceable =
            measurables
                .firstOrNull { it.layoutId == IndicatorLayoutIdTag }
                ?.measure(
                    Constraints.fixed(
                        width = animatedIndicatorWidth,
                        height = indicatorHeight
                    )
                )

        val labelPlaceable =
            label?.let {
                measurables
                    .first { it.layoutId == LabelLayoutIdTag }
                    .measure(
                        // Measure with loose constraints for height as we don't want the label to
                        // take up more space than it needs
                        constraints.copy(minHeight = 0)
                    )
            }

        if (label == null) {
            placeIcon(iconPlaceable, indicatorRipplePlaceable, indicatorPlaceable, constraints)
        } else {
            placeLabelAndIcon(
                labelPlaceable!!,
                iconPlaceable,
                indicatorRipplePlaceable,
                indicatorPlaceable,
                constraints,
                alwaysShowLabel,
                animationProgress
            )
        }
    }
}
fun MeasureScope.placeLabelAndIcon(
    labelPlaceable: Placeable,
    iconPlaceable: Placeable,
    indicatorRipplePlaceable: Placeable,
    indicatorPlaceable: Placeable?,
    constraints: Constraints,
    alwaysShowLabel: Boolean,
    animationProgress: Float,
): MeasureResult {
    val height = constraints.maxHeight

    // Label should be `ItemVerticalPadding` from the bottom
    val labelY = height - labelPlaceable.height - NavigationBarItemVerticalPadding.roundToPx()

    // Icon (when selected) should be `ItemVerticalPadding` from the top
    val selectedIconY = NavigationBarItemVerticalPadding.roundToPx()
    val unselectedIconY =
        if (alwaysShowLabel) selectedIconY else (height - iconPlaceable.height) / 2

    // How far the icon needs to move between unselected and selected states.
    val iconDistance = unselectedIconY - selectedIconY

    // The interpolated fraction of iconDistance that all placeables need to move based on
    // animationProgress.
    val offset = (iconDistance * (1 - animationProgress)).roundToInt()

    val containerWidth = constraints.maxWidth

    val labelX = (containerWidth - labelPlaceable.width) / 2
    val iconX = (containerWidth - iconPlaceable.width) / 2

    val rippleX = (containerWidth - indicatorRipplePlaceable.width) / 2
    val rippleY = selectedIconY - IndicatorVerticalPadding.roundToPx()

    return layout(containerWidth, height) {
        indicatorPlaceable?.let {
            val indicatorX = (containerWidth - it.width) / 2
            val indicatorY = selectedIconY - IndicatorVerticalPadding.roundToPx()
            it.placeRelative(indicatorX, indicatorY + offset)
        }
        if (alwaysShowLabel || animationProgress != 0f) {
            labelPlaceable.placeRelative(labelX, labelY + offset)
        }
        iconPlaceable.placeRelative(iconX, selectedIconY + offset)
        indicatorRipplePlaceable.placeRelative(rippleX, rippleY + offset)
    }
}
fun MeasureScope.placeIcon(
    iconPlaceable: Placeable,
    indicatorRipplePlaceable: Placeable,
    indicatorPlaceable: Placeable?,
    constraints: Constraints
): MeasureResult {
    val width = constraints.maxWidth
    val height = constraints.maxHeight

    val iconX = (width - iconPlaceable.width) / 2
    val iconY = (height - iconPlaceable.height) / 2

    val rippleX = (width - indicatorRipplePlaceable.width) / 2
    val rippleY = (height - indicatorRipplePlaceable.height) / 2

    return layout(width, height) {
        indicatorPlaceable?.let {
            val indicatorX = (width - it.width) / 2
            val indicatorY = (height - it.height) / 2
            it.placeRelative(indicatorX, indicatorY)
        }
        iconPlaceable.placeRelative(iconX, iconY)
        indicatorRipplePlaceable.placeRelative(rippleX, rippleY)
    }
}


private const val IndicatorRippleLayoutIdTag: String = "indicatorRipple"

private const val IndicatorLayoutIdTag: String = "indicator"

private const val IconLayoutIdTag: String = "icon"

private const val LabelLayoutIdTag: String = "label"


private const val ItemAnimationDurationMillis: Int = 100

/*@VisibleForTesting*/
internal val NavigationBarItemHorizontalPadding: Dp = 8.dp

/*@VisibleForTesting*/
internal val NavigationBarItemVerticalPadding: Dp = 16.dp

private val IndicatorVerticalPadding: Dp =
    (NavigationBarTokens.ActiveIndicatorHeight - NavigationBarTokens.IconSize) / 2
private val IndicatorHorizontalPadding: Dp =
    (NavigationBarTokens.ActiveIndicatorWidth - NavigationBarTokens.IconSize) / 2

private val IndicatorVerticalOffset: Dp = 12.dp

object NavigationBarTokens {
    val ActiveIndicatorHeight = 32.0.dp
    val ActiveIndicatorShape = ShapeKeyTokens.CornerFull
    val ActiveIndicatorWidth = 64.0.dp
    val IconSize = 24.0.dp
}
enum class ShapeKeyTokens {
    CornerFull,
}
