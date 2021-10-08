package com.maungedev.data.source.local

import com.maungedev.data.source.local.dao.EventDao
import com.maungedev.data.source.local.dao.UserDao
import com.maungedev.data.source.local.entity.EventEntity
import com.maungedev.data.source.local.entity.UserEntity

class LocalDataSource(
    private val userDao: UserDao,
    private val eventDao: EventDao
) {
    suspend fun insertUser(userEntity: UserEntity): Unit =
        userDao.insertUser(userEntity)

    suspend fun insertEvent(eventEntity: EventEntity): Unit =
        eventDao.insertEvent(eventEntity)
}