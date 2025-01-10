package com.rure.knr_takingattendance.presentation.component.option

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.rure.knr_takingattendance.presentation.component.Picker
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun BirthPicker(
    date: LocalDate,
    modifier: Modifier = Modifier,
    onItemChanged: (LocalDate) -> Unit
) {

    Log.d("BirthPicker", "------------------------------------------------")

    val selectedYear = remember { mutableStateOf(date.year) }
    val yearList = remember { ((date.year - 70)..date.year).toList().reversed() }

    val selectedMonth = remember { mutableStateOf(date.monthValue ) }
    val monthList = remember { (1..12).toList() }

    val selectedDay = remember { mutableStateOf(date.dayOfMonth) }
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

    Row(horizontalArrangement = Arrangement.SpaceEvenly)  {
        Picker(yearList, selectedYear.value,  modifier = Modifier.weight(1f)) {
            selectedYear.value = it
            onItemChanged(getSelectedDate())
        }
        Picker(monthList, selectedMonth.value,  modifier = Modifier.weight(1f)) {
            selectedMonth.value = it
            onItemChanged(getSelectedDate())
        }

        Picker(dayList.value, selectedDay.value,  modifier = Modifier.weight(1f), true) {
            selectedDay.value = it
            onItemChanged(getSelectedDate())
        }
    }


}