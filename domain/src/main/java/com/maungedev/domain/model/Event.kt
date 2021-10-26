package com.maungedev.domain.model

data class Event(
    val uid: String,
    val eventName: String,
    val eventType: String,
    val eventCategory: String,
    val price: Long,
    val date: Long,
    val time: String,
    val location: String,
    val linkRegistration: String,
    val description: String,
    val prerequisite: String,
    val eventCover: String,
    val numbersOfView: Int,
    val numbersOfRegistrationClick: Int,
    val organizer: String,
    val organizerUid: String
) {
}