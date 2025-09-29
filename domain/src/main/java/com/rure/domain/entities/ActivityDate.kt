package com.rure.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(
    tableName = "activity_date"
)
data class ActivityDate(
    @PrimaryKey( false) val date: LocalDate,
)
