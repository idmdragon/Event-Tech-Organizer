package com.maungedev.data.source.local

import com.maungedev.data.source.local.dao.UserDao
import com.maungedev.data.source.local.entity.UserEntity

class LocalDataSource(
    private val userDao: UserDao
) {
    suspend fun insertUser(userEntity: UserEntity): Unit =
        userDao.insertUser(userEntity)

}