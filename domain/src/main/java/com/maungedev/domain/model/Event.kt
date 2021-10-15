package com.maungedev.domain.model

data class Event(
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
    val organizer: String,
    val organizerUid: String
) {
}