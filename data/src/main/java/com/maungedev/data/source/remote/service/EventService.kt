package com.maungedev.data.source.remote.service

import android.net.Uri
import com.maungedev.data.constant.FirebaseConstant
import com.maungedev.data.source.remote.FirebaseResponse
import com.maungedev.data.source.remote.response.CompetitionCategoryResponse
import com.maungedev.data.source.remote.response.ConferenceCategoryResponse
import com.maungedev.data.source.remote.response.EventResponse
import com.maungedev.domain.model.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class EventService : FirebaseService() {

    fun insertEvent(event: Event, imageUri: Uri): Flow<FirebaseResponse<EventResponse>> =
        flow {
            val eventUid = generateDocumentId(FirebaseConstant.FirebaseCollection.EVENT_COLLECTION)

            uploadPicture(
                FirebaseConstant.FirebaseCollection.EVENT_COLLECTION,
                eventUid,
                imageUri
            ).first { response ->
                when (response) {
                    is FirebaseResponse.Success -> {
                        val imageDownloadUrl = response.data
                        val updateEventData = event.copy(
                            uid = eventUid,
                            eventCover = imageDownloadUrl
                        )
                        addArrayStringValueAtDocField(
                            FirebaseConstant.FirebaseCollection.USER,
                            getCurrentUserId(),
                            FirebaseConstant.Field.MY_EVENT,
                            eventUid
                        )

                        emitAll(
                            setDocument<Event, EventResponse>(
                                FirebaseConstant.FirebaseCollection.EVENT_COLLECTION,
                                eventUid,
                                updateEventData
                            )
                        )
                    }
                    is FirebaseResponse.Error -> {
                        emit(FirebaseResponse.Error(response.errorMessage))
                    }
                    FirebaseResponse.Empty -> {
                        emit(FirebaseResponse.Empty)
                    }
                }
                true
            }
        }


    fun getMyEvents(ids: List<String>):Flow<FirebaseResponse<List<EventResponse>>> =
        getDocumentsWhereIds(FirebaseConstant.FirebaseCollection.EVENT_COLLECTION,FirebaseConstant.Field.UID,ids)

    fun getAllConferenceCategory(): Flow<FirebaseResponse<List<ConferenceCategoryResponse>>> =
        getCollection(FirebaseConstant.FirebaseCollection.CONFERENCE_CATEGORY_COLLECTION)

    fun getAllCompetitionCategory(): Flow<FirebaseResponse<List<CompetitionCategoryResponse>>> =
        getCollection(FirebaseConstant.FirebaseCollection.COMPETITION_CATEGORY_COLLECTION)

    fun getEventById(id: String): Flow<FirebaseResponse<EventResponse>> =
        getDocument(FirebaseConstant.FirebaseCollection.EVENT_COLLECTION,id)

    fun updateEvent(event: Event): Flow<FirebaseResponse<EventResponse>> =
        setDocument(
            FirebaseConstant.FirebaseCollection.EVENT_COLLECTION,
            event.uid,
            event
        )
}