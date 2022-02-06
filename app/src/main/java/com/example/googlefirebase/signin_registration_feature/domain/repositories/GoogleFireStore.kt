package com.example.googlefirebase.signin_registration_feature.domain.repositories

import android.content.Context
import android.util.Log
import com.example.googlefirebase.signin_registration_feature.domain.models.Spot
import com.example.googlefirebase.signin_registration_feature.domain.models.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize

class GoogleFireStore(context: Context) {

    // TAG
    private val TAG = "GoogleRegistrationRepo"

    // Member Fields
    private val usersCollection = "Users"
    private val spotsCollection = "Spots"
    private val context = context

    init {
        Firebase.initialize(context)
    }
    // Access a Cloud Firestore Instance
    val firestoreDatabase = Firebase.firestore

    // Construct a Hash Map Object to insert user into Firestore
    fun constructHashMapForNewUser(user: User): HashMap<String, String?> {
        return hashMapOf(
            "username" to user.userName,
            "firstname" to user.firstName,
            "lastname" to user.lastName,
            "password" to user.userPassword,
            "confirmPassword" to user.confirmedPassword
        )
    }

    // Construct a Hash Map Object to insert spot into Firestore
    fun constructHashMapForNewSpot(spot: Spot): HashMap<String, String?> {
        return hashMapOf(
            "latitude" to spot.spotLatLng.latitude.toString(),
            "longitude" to spot.spotLatLng.longitude.toString(),
            "name" to spot.spotName,
            "spotCity" to spot.spotCity,
            "spotState" to spot.spotState
        )
    }

    fun insertUserIntoGoogleFireStore(user: User){
        val hashMapUser = constructHashMapForNewUser(user);

        firestoreDatabase.collection(usersCollection).add(hashMapUser).addOnSuccessListener{ documentReference ->
            Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
        }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    fun insertSpotIntoGoogleFireStore(spot: Spot){
        val hashMapSpot = constructHashMapForNewSpot(spot)

        firestoreDatabase.collection(spotsCollection).add(hashMapSpot).addOnSuccessListener { documentReference ->
            Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
        }.addOnFailureListener{ e ->
            Log.w(TAG, "Error adding document", e)
        }
    }
}