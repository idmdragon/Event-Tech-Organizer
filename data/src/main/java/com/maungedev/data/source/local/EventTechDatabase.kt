package com.maungedev.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.maungedev.data.source.local.dao.UserDao
import com.maungedev.data.source.local.entity.UserEntity

@Database(entities = [UserEntity::class],version = 1, exportSchema = false)
abstract class EventTechDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}