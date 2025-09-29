package com.rure.domain.entities

import java.time.LocalDate

@Entity(
    tableName = "activity_date"
)
data class ActivityDate(
    @PrimaryKey( false) val date: LocalDate,
)
