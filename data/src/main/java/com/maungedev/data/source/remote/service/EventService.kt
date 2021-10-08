package com.maungedev.data.source.remote.service

import android.net.Uri
import android.util.Log
import com.maungedev.data.constant.FirebaseConstant.FirebaseCollection.EVENT_COLLECTION
import com.maungedev.data.source.remote.FirebaseResponse
import com.maungedev.data.source.remote.response.EventResponse
import com.maungedev.domain.model.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class EventService : FirebaseService() {

    fun insertEvent(event: Event, imageUri: Uri): Flow<FirebaseResponse<EventResponse>> =
        flow {
            val eventUid = generateDocumentId(EVENT_COLLECTION)
            Log.d("AddEventDEBUGING","EventService isi event $event")
            Log.d("AddEventDEBUGING","EventService isi Uri $imageUri")

            uploadPicture(EVENT_COLLECTION, eventUid, imageUri).first { response ->
                Log.d("AddEventDEBUGING","uploadPicture response $response ")
                when (response) {
                    is FirebaseResponse.Success -> {
                        val imageDownloadUrl = response.data
                        val updateEventData = event.copy(
                            uid = eventUid,
                            eventCover = imageDownloadUrl
                        )
                        emitAll(
                            setDocument<Event, EventResponse>(
                                EVENT_COLLECTION,
                                eventUid,
                                updateEventData
                            )
                        )
                    }
                    is FirebaseResponse.Error -> {
                        emit(FirebaseResponse.Error(response.errorMessage))
                        Log.d("AddEventDEBUGING","EventService Error ${response.errorMessage}")
                    }
                    FirebaseResponse.Empty -> {
                        emit(FirebaseResponse.Empty)
                        Log.d("AddEventDEBUGING","EventService Empty ")
                    }
                }
                true
            }

        }


}