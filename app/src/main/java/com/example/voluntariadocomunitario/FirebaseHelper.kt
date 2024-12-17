package com.example.voluntariadocomunitario

import VoluntaryAct
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.tasks.await

class FirebaseHelper {

    private val db = FirebaseFirestore.getInstance()
    private val actsCollection = db.collection("voluntary_acts")

    suspend fun getVoluntaryActs(): List<VoluntaryAct> {
        return try {
            val snapshot = actsCollection.get().await()
            snapshot.toObjects()
        } catch (e: Exception) {
            emptyList()
        }
    }
}