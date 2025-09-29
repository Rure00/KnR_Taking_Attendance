package com.rure.presentation.component.option

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.text.TextStyle
import com.rure.presentation.component.Picker
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun DatePicker(
    selectedDate: LocalDate,
    itemTextStyle: TextStyle,
    modifier: Modifier = Modifier,
    itemModifier: Modifier = Modifier,

    visibleItemNum: Int = 3,
    dividerColor: Color = Color.Black,
    onItemChanged: (LocalDate) -> Unit
) {
    val selectedYear = remember { mutableStateOf(selectedDate.year) }
    val yearList = remember { ((selectedDate.year - 70)..selectedDate.year).toList().reversed() }

    val selectedMonth = remember { mutableStateOf(selectedDate.monthValue ) }
    val monthList = remember { (1..12).toList() }

    val selectedDay = remember { mutableStateOf(selectedDate.dayOfMonth) }
    val dayList = remember {
        derivedStateOf {
            (1 ..YearMonth.of(selectedYear.value, selectedMonth.value).lengthOfMonth()).toList()
        }
    }

    fun getSelectedDate(): LocalDate {
        Log.d("BirthPicker", "selectedDay is ${selectedDay.value}")
        Log.d("BirthPicker", "selectedMonth is ${selectedMonth.value}")
        Log.d("BirthPicker", "lastDay is ${dayList.value.last()}")
        if(dayList.value.last() < selectedDay.value) {
            Log.d("BirthPicker", "${selectedDay.value} changed to ${dayList.value.last()}")
            selectedDay.value = dayList.value.last()
        }

        return LocalDate.of(
            selectedYear.value, selectedMonth.value, selectedDay.value
        )
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly
    )  {
        Picker(
            items = yearList,
            defaultState = selectedYear.value,
            modifier = itemModifier.weight(1f),
            visibleItemsCount = visibleItemNum,
            dividerColor = dividerColor,
            textStyle = itemTextStyle
        ) {
            selectedYear.value = it
            onItemChanged(getSelectedDate())
        }


        Picker(
            items = monthList,
            defaultState = selectedMonth.value,
            modifier = itemModifier.weight(1f),
            visibleItemsCount = visibleItemNum,
            dividerColor = dividerColor,
            textStyle = itemTextStyle
        ) {
            selectedMonth.value = it
            onItemChanged(getSelectedDate())
        }

        Picker(
            items = dayList.value,
            defaultState = selectedDay.value,
            modifier = itemModifier.weight(1f),
            visibleItemsCount = visibleItemNum,
            dividerColor = dividerColor,
            textStyle = itemTextStyle
        ) {
            selectedDay.value = it
            onItemChanged(getSelectedDate())
        }
    }


}