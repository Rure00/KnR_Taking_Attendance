package com.rure.knr_takingattendance.presentation.screen.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.rure.knr_takingattendance.R
import com.rure.knr_takingattendance.data.entities.Position
import com.rure.knr_takingattendance.presentation.component.PositionButton
import com.rure.knr_takingattendance.ui.theme.Black
import com.rure.knr_takingattendance.ui.theme.Typography


@Composable
 fun WritePositionPage(positionMap: Map<Position, Boolean>, onChange: (Position, Boolean) -> Unit) {
    Column(
        modifier = Modifier.padding(horizontal = 10.dp)
    ) {
        Text(
            text = stringResource(R.string.enter_position),
            style = Typography.titleSmall,
            color = Black
        )

        Spacer(modifier = Modifier.height(10.dp))

        val textStyle = Typography.bodySmall

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            PositionButton(
                isSelected = positionMap[Position.Forward] ?: false,
                position = Position.Forward,
                textStyle = textStyle
            ) { isChecked, position ->
                onChange(position, isChecked)
            }

            PositionButton(
                isSelected = positionMap[Position.Defender] ?: false,
                position = Position.Defender,
                textStyle = textStyle
            ) { isChecked, position ->
                onChange(position, isChecked)
            }

            PositionButton(
                isSelected = positionMap[Position.Midfielder] ?: false,
                position = Position.Midfielder,
                textStyle = textStyle
            ) { isChecked, position ->
                onChange(position, isChecked)
            }

            PositionButton(
                isSelected = positionMap[Position.GoalKeeper] ?: false,
                position = Position.GoalKeeper,
                textStyle = textStyle
            ) { isChecked, position ->
                onChange(position, isChecked)
            }
        }
    }

}