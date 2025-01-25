package com.rure.knr_takingattendance.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rure.knr_takingattendance.data.dao.ActivityDateDao
import com.rure.knr_takingattendance.data.dao.ParticipationToMemberDao
import com.rure.knr_takingattendance.data.dao.MemberDao
import com.rure.knr_takingattendance.data.entities.ActivityDate
import com.rure.knr_takingattendance.data.entities.Member
import com.rure.knr_takingattendance.data.entities.ParticipationToMember
import com.rure.knr_takingattendance.data.utils.LocalDateConverter
import com.rure.knr_takingattendance.data.utils.PositionMapConverter

@Database(entities = [Member::class, ActivityDate::class, ParticipationToMember::class], version = 1)
@TypeConverters(LocalDateConverter::class, PositionMapConverter::class)
abstract class AppRoomDataBase: RoomDatabase() {
    abstract val memberDao: MemberDao
    abstract val participationToMemberDao: ParticipationToMemberDao
    abstract val activityDateDao: ActivityDateDao
}