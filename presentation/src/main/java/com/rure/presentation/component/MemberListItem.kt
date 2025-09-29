package com.rure.presentation.component

import android.util.Log
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rure.presentation.R
import com.rure.data.entities.Member
import com.rure.presentation.intent.MemberIntent
import com.rure.presentation.viewmodels.MemberViewModel
import com.rure.presentation.ui.theme.BackgroundRed
import com.rure.presentation.ui.theme.Black
import com.rure.presentation.ui.theme.LightGray
import com.rure.presentation.ui.theme.LightGray2
import com.rure.presentation.ui.theme.Typography
import com.rure.presentation.ui.theme.White


private const val MaxDragOffset = 150f
private const val DragCompensation = -20f


@Composable
fun MemberListItem(
    member: Member,
    toDetail: (Int) -> Unit,
    onDelete: () -> Unit,
) {

    val showDialog = remember { mutableStateOf(false) }

    val dragOffset = remember { mutableFloatStateOf(0f) }


    val dragOffsetAnimation = animateFloatAsState(
        targetValue = dragOffset.floatValue,
        animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy, stiffness = Spring.StiffnessLow),
        label = "dragOffsetAnimation"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
            .background(Black)
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onHorizontalDrag = { _, dragAmount ->
                        dragOffset.floatValue = (dragOffset.floatValue + dragAmount).coerceIn(
                            0f, MaxDragOffset
                        )
                    },
                    onDragEnd = {
                        Log.d("MemberListItem", "dragOffset: ${dragOffset.floatValue}")
                        if (dragOffset.floatValue > MaxDragOffset + DragCompensation) {
                            showDialog.value = true
                        }
                        dragOffset.floatValue = 0f
                    }
                )
            }
    ) {
        Row(modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(BackgroundRed)
            .padding(start = 10.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.delete_img),
                contentDescription = null,
                modifier = Modifier.size(28.dp)
            )
            Text(
                modifier = Modifier.padding(start = 10.dp, end = 10.dp),
                text = stringResource(R.string.delete_str),
                style = Typography.bodyMedium,
                color = White
            )
        }


        MemberBar(
            animationOffset = dragOffsetAnimation.value,
            member = member,
            toDetail = toDetail
        )
    }


    if(showDialog.value)
        DeleteConfirmDialog(
            member = member,
            onConfirm = {
                onDelete()
                showDialog.value = false
            },
            onCancel = { showDialog.value = false }
        )

}

@Composable
private fun MemberBar(
    animationOffset: Float,
    member: Member,
    toDetail: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .offset(x = animationOffset.dp)
            .fillMaxWidth()
            .fillMaxHeight()
            .background(White)
            .padding(horizontal = 16.dp, vertical = 11.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f),
            text = member.name,
            style = Typography.bodyMedium
        )

        Image(
            painter = painterResource(R.drawable.info_img),
            contentDescription = null,
            modifier = Modifier.size(20.dp).clickable { toDetail(member.id) }
        )
    }
}

@Composable
fun DeleteConfirmDialog(
    member: Member,
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    Dialog(
        onDismissRequest = { onCancel() },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
        ),
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .clip(shape = RoundedCornerShape(8.dp))
                .background(color = White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.padding(20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = member.name,
                    color = Black,
                    style = Typography.bodyLarge
                )
                Text(
                    text = stringResource(R.string.ask_delete),
                    color = Black,
                    style = Typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(color = LightGray))

            Row {
                val height = remember {
                    mutableStateOf(0.dp)
                }
                val density = LocalDensity.current
                Text(
                    text = stringResource(R.string.cancel_str),
                    color = LightGray2,
                    style = Typography.bodyMedium,
                    modifier = Modifier.weight(1f).clickable { onCancel() }.onSizeChanged {
                        height.value = with(density) { it.height.toDp() }
                        Log.d("memberListItem", "height: $height")
                    }.padding(vertical = 6.dp),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier
                    .height(height.value)
                    .width(1.dp)
                    .background(color = LightGray))
                Text(
                    text = stringResource(R.string.confirm_str),
                    color = Black,
                    style = Typography.bodyLarge,
                    modifier = Modifier.weight(1f).clickable { onConfirm() }.padding(vertical = 6.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }

}