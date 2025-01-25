package com.rure.knr_takingattendance.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.rure.knr_takingattendance.data.entities.Member
import com.rure.knr_takingattendance.data.entities.MemberParticipation
import com.rure.knr_takingattendance.data.entities.ParticipationToMember
import java.time.LocalDate

@Dao
interface ParticipationToMemberDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMemberParticipation(memberParticipation: ParticipationToMember)
    @Update
    fun updateMemberParticipation(memberParticipation: ParticipationToMember)
    @Delete
    fun deleteMemberParticipation(memberParticipation: ParticipationToMember)

    @Query(
        "SELECT * FROM participation_to_member AS participation" +
        "INNER JOIN members ON members.id == member_id " +
        "WHERE date == :date"
    )
    fun getMemberParticipationWhen(date: LocalDate): Map<Member, ParticipationToMember>

//    @Query(
//        "SELECT * FROM member_participation WHERE date == :date"
//    )
//    fun getMemberParticipationWhen(date: LocalDate): List<MemberParticipation>


    @Query("SELECT * FROM participation_to_member WHERE member_id == :memberId")
    fun getMemberParticipationByMember(memberId: Int): List<ParticipationToMember>

}