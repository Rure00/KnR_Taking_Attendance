package com.rure.knr_takingattendance.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rure.knr_takingattendance.data.dao.MemberParticipationDao
import com.rure.knr_takingattendance.data.dao.MemberDao
import com.rure.knr_takingattendance.data.entities.Member
import com.rure.knr_takingattendance.data.entities.MemberParticipation
import com.rure.knr_takingattendance.data.utils.LocalDateConverter
import com.rure.knr_takingattendance.data.utils.PositionMapConverter

@Database(entities = [Member::class, MemberParticipation::class], version = 1)
@TypeConverters(LocalDateConverter::class, PositionMapConverter::class)
abstract class AppRoomDataBase: RoomDatabase() {
    abstract val memberDao: MemberDao
    abstract val memberParticipationDao: MemberParticipationDao
}