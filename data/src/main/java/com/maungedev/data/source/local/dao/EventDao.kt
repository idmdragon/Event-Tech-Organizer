package com.maungedev.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.maungedev.data.source.local.entity.CompetitionCategoryEntity
import com.maungedev.data.source.local.entity.ConferenceCategoryEntity
import com.maungedev.data.source.local.entity.EventEntity
import com.maungedev.data.source.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: EventEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConferenceCategory(conferenceCategoryEntity: List<ConferenceCategoryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompetitionCategory(competitionCategoryEntity: List<CompetitionCategoryEntity>)

    @Query("SELECT * FROM conference_category")
    fun selectAllConferenceCategory(): Flow<List<ConferenceCategoryEntity>>

    @Query("SELECT * FROM competition_category")
    fun selectAllCompetitionCategory(): Flow<List<CompetitionCategoryEntity>>

    @Query("SELECT * FROM Event")
    fun selectAllMyEvents(): Flow<List<EventEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvents(listEvent: List<EventEntity>)

    @Query("SELECT * FROM event WHERE uid = :uid")
    fun selectEventByUid(uid: String): Flow<EventEntity>

    @Query("SELECT COUNT(eventName) FROM Event")
    fun selectTotalEvent(): Int

    @Query("SELECT SUM(numbersOfRegistrationClick) FROM Event")
    fun selectTotalRegistrationClick(): Int

    @Query("SELECT SUM(numbersOfView) FROM Event")
    fun selectTotalView(): Int

}