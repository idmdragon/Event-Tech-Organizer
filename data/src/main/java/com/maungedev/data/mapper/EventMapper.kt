package com.maungedev.data.mapper

import com.maungedev.data.source.local.entity.EventEntity
import com.maungedev.data.source.remote.response.EventResponse


fun EventResponse.toEntity(): EventEntity =
    EventEntity(
        uid,
        eventName,
        eventType,
        eventCategory,
        price,
        date,
        time,
        location,
        linkRegistration,
        description,
        prerequisite,
        eventCover,
        numbersOfView,
        numbersOfRegistrationClick,
        favoriteBy,
        organizer,
        organizerUid
    )