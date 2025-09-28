package com.rure.knr_takingattendance.presentation.component

import android.util.Log
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.wear.compose.material3.Text
import com.rure.knr_takingattendance.ui.theme.Black
import com.rure.knr_takingattendance.ui.theme.Gray
import com.rure.knr_takingattendance.ui.theme.Typography
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@Composable
fun Picker(
    items: List<Int>,
    defaultState: Int,
    modifier: Modifier = Modifier,
    visibleItemsCount: Int = 3,
    textModifier: Modifier = Modifier,
    textStyle: TextStyle = Typography.bodyMedium,
    selectedColor: Color = Black,
    unselectedColor: Color = Gray,
    dividerColor: Color = Black,
    onItemChanged: (Int) -> Unit,
) {
    val startIndex = items.indexOf(defaultState)

    val visibleItemsMiddle = visibleItemsCount / 2
    val listScrollCount = Integer.MAX_VALUE
    val listScrollMiddle = listScrollCount / 2
    val listStartIndex = listScrollMiddle - listScrollMiddle % items.size - visibleItemsMiddle + startIndex

    val selectedItem = remember { mutableStateOf(defaultState) }
    val selectedItemIndex = remember { mutableStateOf(listStartIndex) }


    fun getItem(index: Int) = items[index % items.size]

    val listState = remember(items.size) { LazyListState(firstVisibleItemIndex = listStartIndex) }

    val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)

    val itemHeightPixels = remember { mutableStateOf(0) }
    val itemHeightDp = pixelsToDp(itemHeightPixels.value)

    val fadingEdgeGradient = remember {
        Brush.verticalGradient(
            0f to Color.Transparent,
            0.5f to Color.Black,
            1f to Color.Transparent
        )
    }

    LaunchedEffect(listState, items.size) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .map { index -> getItem(index + visibleItemsMiddle) }
            .distinctUntilChanged()
            .collect { item ->
                selectedItemIndex.value = listState.firstVisibleItemIndex + visibleItemsCount/2
                selectedItem.value = item
                onItemChanged(selectedItem.value)

                Log.d("Picker", "selectedItemIndex: ${selectedItemIndex.value}")
            }
    }

    Box(modifier = modifier) {

        LazyColumn(
            state = listState,
            flingBehavior = flingBehavior,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .height(itemHeightDp * visibleItemsCount)
                .fadingEdge(fadingEdgeGradient)
        ) {
            items(listScrollCount) { index ->
                Text(
                    text = getItem(index).toString(),
                    maxLines = 1,
                    //overflow = TextOverflow.Ellipsis,
                    style = textStyle,
                    modifier = Modifier
                        .onSizeChanged { size -> itemHeightPixels.value = size.height }
                        .then(textModifier),
                    color = if(index == selectedItemIndex.value) selectedColor
                        else unselectedColor
                )
            }
        }

        HorizontalDivider(
            modifier = Modifier.offset(y = itemHeightDp * visibleItemsMiddle),
            color = dividerColor
        )

        HorizontalDivider(
            modifier = Modifier.offset(y = itemHeightDp * (visibleItemsMiddle + 1)),
            color = dividerColor
        )

    }

}

private fun Modifier.fadingEdge(brush: Brush) = this
    .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
    .drawWithContent {
        drawContent()
        drawRect(brush = brush, blendMode = BlendMode.DstIn)
    }

@Composable
private fun pixelsToDp(pixels: Int) = with(LocalDensity.current) { pixels.toDp() }