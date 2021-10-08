package com.maungedev.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.maungedev.data.source.local.dao.EventDao
import com.maungedev.data.source.local.dao.UserDao
import com.maungedev.data.source.local.entity.EventEntity
import com.maungedev.data.source.local.entity.ListConverter
import com.maungedev.data.source.local.entity.UserEntity

@Database(entities = [UserEntity::class,EventEntity::class],version = 1, exportSchema = false)
@TypeConverters(ListConverter::class)
abstract class EventTechDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun eventDao(): EventDao
}