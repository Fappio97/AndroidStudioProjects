package it.unical.demacs.siieco.data.remote

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import it.unical.demacs.siieco.data.local.PreferenceRepository

object FirestoreRepository {

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    private fun add(collection: String, document: Map<String, Any>, userId: String) {
        db.collection(collection)
            .document(userId)
            .set(document, SetOptions.merge())
            .addOnSuccessListener {
                Log.d("FirestoreRepository", "DocumentSnapshot successfully written!")
            }
            .addOnFailureListener { e ->
                Log.w("FirestoreRepository", "Error adding document", e)
            }
    }

    fun addUser(user: Map<String, String>, userId: String) {
        add("user_type", user, userId)
    }
/*
    private fun read(collection: String) {
        db.collection(collection)
            .get()
            .addOnSuccessListener { result ->

                for (document in result) {
                    Log.d("FirestoreRepository", "${document.id} => ${document.data}")
                }

            }
            .addOnFailureListener { exception ->
                Log.w("FirestoreRepository", "Error getting documents.", exception)
            }
    }
*/
    private fun readOnlyOneRecord(collection: String, keyValue: String, preferenceRepository: PreferenceRepository) {
        db.collection(collection)
            .get()
            .addOnSuccessListener { result ->

                for (document in result) {
                    Log.d("FirestoreRepository", "${document.id} => ${document.data}")
                    if (document.data.containsValue(keyValue)) {
                        val string: String =  document.data["type"] as String
                        Log.d("KEY VALUE",string)
                    }
                }

            }
            .addOnFailureListener { exception ->
                Log.w("FirestoreRepository", "Error getting documents.", exception)
            }
    }
/*
    private fun readOnlyOneRecordRealTime(collection: String, documentKey: String, key: String) {
        val docRef = db.collection(collection).document(documentKey)
        docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w("FALLITO", "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                Log.d("FATTO", "Current data: ${snapshot.data}")
                Log.d("FATTO", "Current data: ${snapshot.get(key)}")
                admin.text = snapshot.get(key).toString()
            } else {
                Log.d("NULLO", "Current data: null")
            }
        }
    }

    fun readUser(userId: String, key: String) {
        readOnlyOneRecordRealTime("user_type", userId, key)
    }
*/

    fun getPostList(): Task<QuerySnapshot> {
        return db
            .collection("post")
            .orderBy("date", Query.Direction.DESCENDING)
            .get()
    }
}