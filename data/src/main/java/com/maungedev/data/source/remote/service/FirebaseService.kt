package com.maungedev.data.source.remote.service

import android.net.Uri
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.maungedev.data.source.remote.FirebaseResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import java.net.URI

abstract class FirebaseService {

    val auth = Firebase.auth
    val firestore = Firebase.firestore
    val storage = Firebase.storage

    fun getCurrentUserId() = auth.currentUser?.uid

    fun generateDocumentId(collection: String): String =
        firestore.collection(collection).document().id

    inline fun <reified ResponseType> getCollection(
        collection: String,
        docId: String,
        subCollection: String
    ): Flow<FirebaseResponse<List<ResponseType>>> =
        flow {
            val result = firestore
                .collection(collection)
                .document(docId)
                .collection(subCollection)
                .get()
                .await()

            if (result.isEmpty) {
                emit(FirebaseResponse.Empty)
            } else {
                emit(FirebaseResponse.Success(result.toObjects(ResponseType::class.java)))
            }
        }.catch {
            emit(FirebaseResponse.Error(it.message.toString()))
        }.flowOn(Dispatchers.IO)

    fun createUserWithEmailAndPassword(
        email: String,
        password: String
    ): Flow<FirebaseResponse<String>> =
        flow {
            val createUser = auth.createUserWithEmailAndPassword(email, password).await()
            val user = createUser.user
            if (user != null) {
                emit(FirebaseResponse.Success(user.uid))
            } else {
                emit(FirebaseResponse.Empty)
            }
        }.catch {
            emit(FirebaseResponse.Error(it.message.toString()))
        }.flowOn(Dispatchers.IO)


    inline fun <RequestType, reified ResponseType> setDocument(
        collection: String,
        docId: String,
        document: RequestType
    ): Flow<FirebaseResponse<ResponseType>> =
        flow {
            firestore
                .collection(collection)
                .document(docId)
                .set(document as Any)
                .await()

            emitAll(getDocument<ResponseType>(collection, docId))
        }.catch {
            emit(FirebaseResponse.Error(it.message.toString()))
        }.flowOn(Dispatchers.IO)

    inline fun <reified ResponseType> getDocument(
        collection: String,
        docId: String
    ): Flow<FirebaseResponse<ResponseType>> =
        flow {
            val result = firestore
                .collection(collection)
                .document(docId)
                .get()
                .await()

            if (result.exists()) {
                emit(FirebaseResponse.Success(result.toObject(ResponseType::class.java)!!))
            } else {
                emit(FirebaseResponse.Empty)
            }
        }.catch {
            emit(FirebaseResponse.Error(it.message.toString()))
        }.flowOn(Dispatchers.IO)


    fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): Flow<FirebaseResponse<String>> =
        flow {
            val createUser = auth.signInWithEmailAndPassword(email, password).await()
            val user = createUser.user
            if (user != null) {
                emit(FirebaseResponse.Success(user.uid))
            } else {
                emit(FirebaseResponse.Empty)
            }
        }.catch {
            emit(FirebaseResponse.Error(it.message.toString()))
        }.flowOn(Dispatchers.IO)

    fun uploadPicture(
        reference: String,
        fileName: String,
        pictureURI: Uri
    ): Flow<FirebaseResponse<String>> =
        flow {
            try {
                val filePath = storage.reference
                    .child("$reference/$fileName.jpg")

                filePath.putFile(Uri.parse(pictureURI.toString())).await()
                val downloadUrl = filePath.downloadUrl.await()

                emit(FirebaseResponse.Success(downloadUrl.toString()))
            } catch (e: Exception) {
                emit(FirebaseResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)

    inline fun <reified ResponseType>getCollection(collection: String):Flow<FirebaseResponse<List<ResponseType>>> =
        flow{
            val result = firestore
                .collection(collection)
                .get()
                .await()

            if (result.isEmpty){
                emit(FirebaseResponse.Empty)
            }else{
                emit(FirebaseResponse.Success(result.toObjects(ResponseType::class.java)))
            }
        }.catch {
            emit(FirebaseResponse.Error(it.message.toString()))
        }.flowOn(Dispatchers.IO)
}
