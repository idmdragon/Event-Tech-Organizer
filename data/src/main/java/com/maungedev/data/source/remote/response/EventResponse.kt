package com.maungedev.data.source.remote.response


data class EventResponse(
    val uid: String ="",
    val eventName: String="",
    val eventType: String="",
    val eventCategory: String="",
    val price: Long=0,
    val date: Long= 0,
    val time: String="",
    val location: String="",
    val linkRegistration: String="",
    val description: String="",
    val prerequisite: String="",
    val eventCover: String="",
    val numbersOfView: Int=0,
    val numbersOfRegistrationClick: Int=0,
    val organizer: String ="",
    val organizerUid: String =""
)