package com.rure.knr_takingattendance.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
    isSelected: Boolean,
    position: Position,
    textStyle: TextStyle,
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues =  PaddingValues(vertical = 5.dp, horizontal = 15.dp),
    onClick: (Boolean, Position) -> Unit
) {
    val buttonColor = when(position) {
        Position.Forward -> FwColor
        Position.Midfielder -> MfColor
        Position.Defender -> DfColor
        Position.GoalKeeper -> GkColor
    }
    val unSelectedColor = LightGray


    Text(
        text = position.abbr,
        style = textStyle,
        color = White,
        modifier = modifier.wrapContentSize()
            .clip(shape = RoundedCornerShape(7.dp))
            .background(color = if(isSelected) buttonColor else unSelectedColor)
            .clickable {
                onClick(!isSelected, position)
            }
            .padding(innerPadding)
    )
}