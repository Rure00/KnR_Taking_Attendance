package com.rure.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rure.data.entities.Member
import kotlinx.coroutines.flow.Flow

@Dao
interface MemberDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMember(member: Member): Long
//    @Update
//    fun updateMember(member: Member)
    @Delete
    fun deleteMember(member: Member)

    @Query("SELECT * FROM members WHERE id == :id")
    fun getMemberById(id: Int): Member?

    @Query("SELECT * FROM members")
    fun getAllMembers(): List<Member>

    @Query("SELECT * FROM members")
    fun subscribeMemberFlow(): Flow<List<Member>>

}