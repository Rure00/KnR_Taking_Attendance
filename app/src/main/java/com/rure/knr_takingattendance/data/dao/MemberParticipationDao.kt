package com.rure.knr_takingattendance.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.rure.knr_takingattendance.data.entities.Member
import com.rure.knr_takingattendance.data.entities.MemberParticipation
import java.time.LocalDate

@Dao
interface MemberParticipationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMemberParticipation(memberParticipation: MemberParticipation)
    @Update
    fun updateMemberParticipation(memberParticipation: MemberParticipation)
    @Delete
    fun deleteMemberParticipation(memberParticipation: MemberParticipation)

    @Query(
        "SELECT * FROM member_participation AS participation" +
        "INNER JOIN members ON members.id == member_id " +
        "WHERE date == :date"
    )
    fun getMemberParticipationWhen(date: LocalDate): Map<Member, List<MemberParticipation>>

//    @Query(
//        "SELECT * FROM member_participation WHERE date == :date"
//    )
//    fun getMemberParticipationWhen(date: LocalDate): List<MemberParticipation>


    @Query("SELECT * FROM member_participation WHERE member_id == :memberId")
    fun getMemberParticipationByMember(memberId: Int): List<MemberParticipation>

}