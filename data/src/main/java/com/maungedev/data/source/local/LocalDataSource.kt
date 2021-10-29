package com.maungedev.data.source.local

import com.maungedev.data.source.local.dao.EventDao
import com.maungedev.data.source.local.dao.UserDao
import com.maungedev.data.source.local.entity.CompetitionCategoryEntity
import com.maungedev.data.source.local.entity.ConferenceCategoryEntity
import com.maungedev.data.source.local.entity.EventEntity
import com.maungedev.data.source.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

class LocalDataSource(
    private val userDao: UserDao,
    private val eventDao: EventDao
) {
    suspend fun insertUser(userEntity: UserEntity): Unit =
        userDao.insertUser(userEntity)

    suspend fun clearUser(): Unit =
        userDao.clear()

    fun selectUser(): Flow<UserEntity> =
        userDao.selectCurrentUser()

    suspend fun insertEvent(eventEntity: EventEntity): Unit =
        eventDao.insertEvent(eventEntity)

    suspend fun insertEvents(listEvent: List<EventEntity>): Unit =
        eventDao.insertEvents(listEvent)

    suspend fun clearEvent(): Unit =
        eventDao.clearEvent()

    suspend fun insertConferenceCategory(conferenceCategoryEntity: List<ConferenceCategoryEntity>): Unit =
        eventDao.insertConferenceCategory(conferenceCategoryEntity)

    suspend fun insertCompetitionCategory(competitionCategoryEntity: List<CompetitionCategoryEntity>): Unit =
        eventDao.insertCompetitionCategory(competitionCategoryEntity)

    fun selectAllConferenceCategory(): Flow<List<ConferenceCategoryEntity>> =
        eventDao.selectAllConferenceCategory()

    fun selectAllCompetitionCategory(): Flow<List<CompetitionCategoryEntity>> =
        eventDao.selectAllCompetitionCategory()

    fun selectAllMyEvents(): Flow<List<EventEntity>> =
        eventDao.selectAllMyEvents()

    fun selectEventByUid(uid: String): Flow<EventEntity> =
        eventDao.selectEventByUid(uid)

    fun getTotalEvent(): Int =
        eventDao.selectTotalEvent()

    fun getTotalRegistrationClick(): Int =
        eventDao.selectTotalRegistrationClick()

    fun getTotalView(): Int =
        eventDao.selectTotalView()

    suspend fun deleteEvent(id: String) =
        eventDao.deleteEvent(id)

}