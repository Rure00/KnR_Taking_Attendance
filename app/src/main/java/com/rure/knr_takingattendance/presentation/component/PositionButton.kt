package com.rure.knr_takingattendance.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.rure.knr_takingattendance.data.entities.Position
import com.rure.knr_takingattendance.ui.theme.DfColor
import com.rure.knr_takingattendance.ui.theme.FwColor
import com.rure.knr_takingattendance.ui.theme.GkColor
import com.rure.knr_takingattendance.ui.theme.LightGray
import com.rure.knr_takingattendance.ui.theme.MfColor
import com.rure.knr_takingattendance.ui.theme.White

@Composable
fun PositionButton(
    position: Position,
    textStyle: TextStyle,
    modifier: Modifier = Modifier,
    onClick: (Boolean, Position) -> Unit
) {
    val isSelected = remember { mutableStateOf(false) }
    val buttonColor = when(position) {
        Position.Forward -> FwColor
        Position.Midfielder -> MfColor
        Position.Defender -> DfColor
        Position.GoalKeeper -> GkColor
    }
    val unSelectedColor = LightGray

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(7.dp),
        colors = CardDefaults.cardColors(containerColor = if(isSelected.value) buttonColor else unSelectedColor)
    ) {
        Box(
            modifier = modifier.clickable {
                isSelected.value = !isSelected.value
                onClick(isSelected.value, position)
            },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = position.abbr,
                style = textStyle,
                color = White
            )
        }
    }


}