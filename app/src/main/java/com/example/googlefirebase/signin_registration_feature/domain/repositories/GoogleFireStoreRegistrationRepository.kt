package com.example.googlefirebase.signin_registration_feature.domain.repositories

import android.content.Context
import android.util.Log
import com.example.googlefirebase.signin_registration_feature.domain.models.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize

class GoogleFireStoreRegistrationRepository(context: Context) {

    // TAG
    private val TAG = "GoogleRegistrationRepo"

    // Member Fields
    private val collection = "Users"
    private val context = context

    init {
        Firebase.initialize(context)
    }
    // Access a Cloud Firestore Instance
    val firestoreDatabase = Firebase.firestore

    // Construct a Hash Map Object to insert user
    fun constructHashMapForNewUser(user: User): HashMap<String, String?> {
        return hashMapOf(
            "username" to user.userName,
            "firstname" to user.firstName,
            "lastname" to user.lastName,
            "password" to user.userPassword,
            "confirmPassword" to user.confirmedPassword
        )
    }

    fun insertUserIntoGoogleFireStore(user: User){
        val hashMapUser = constructHashMapForNewUser(user);

        firestoreDatabase.collection(collection).add(hashMapUser).addOnSuccessListener{ documentReference ->
            Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
        }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }
}