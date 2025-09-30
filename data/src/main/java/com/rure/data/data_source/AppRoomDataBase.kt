package com.rure.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rure.data.dao.ActivityDateDao
import com.rure.data.dao.ParticipationToMemberDao
import com.rure.data.dao.MemberDao
import com.rure.domain.models.ActivityDateDto
import com.rure.data.entities.Member
import com.rure.data.entities.ParticipationToMember
import com.rure.core.utils.LocalDateConverter
import com.rure.core.utils.PositionMapConverter

@Database(entities = [Member::class, ActivityDateDto::class, ParticipationToMember::class], version = 1)
@TypeConverters(LocalDateConverter::class, PositionMapConverter::class)
abstract class AppRoomDataBase: RoomDatabase() {
    abstract val memberDao: MemberDao
    abstract val participationToMemberDao: ParticipationToMemberDao
    abstract val activityDateDao: ActivityDateDao
}