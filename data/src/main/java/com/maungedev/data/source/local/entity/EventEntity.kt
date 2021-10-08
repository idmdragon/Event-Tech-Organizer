package com.maungedev.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

@Entity(tableName = "Event")
data class EventEntity(
    @PrimaryKey
    val uid: String,
    val eventName: String,
    val eventType: String,
    val eventCategory: String,
    val price: Long,
    val date: String,
    val time: String,
    val location: String,
    val linkRegistration: String,
    val description: String,
    val prerequisite: String,
    val eventCover: String,
    val numbersOfView: Int,
    val numbersOfRegistrationClick: Int,
    var favoriteBy: List<String>?,
    val organizer: String
)
class ListConverter {
    @TypeConverter
    fun toTorrent(json: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toJson(torrent: List<String>): String {
        val type = object: TypeToken<List<String>>() {}.type
        return Gson().toJson(torrent, type)
    }
}
