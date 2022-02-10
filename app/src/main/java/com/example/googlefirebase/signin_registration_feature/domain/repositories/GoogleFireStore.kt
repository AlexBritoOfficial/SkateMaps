package com.example.googlefirebase.signin_registration_feature.domain.repositories

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.googlefirebase.signin_registration_feature.domain.models.Spot
import com.example.googlefirebase.signin_registration_feature.domain.models.User
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize

class GoogleFireStore(context: Context) {

    // TAG
    private val TAG = "GoogleRegistrationRepo"

    // Member Fields
    private val usersCollection = "Users"
    private val spotsCollection = "Spots"
    private val context = context
    private val listOfSpots: ArrayList<Spot> by lazy {
        ArrayList()
    }

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
            "latitude" to spot.spotLatLng!!.latitude.toString(),
            "longitude" to spot.spotLatLng!!.longitude.toString(),
            "name" to spot.spotName,
            "spotCity" to spot.spotCity,
            "spotState" to spot.spotState
        )
    }

    fun insertUserIntoGoogleFireStore(user: User) {
        // Get an Instance of a HashMap to construct a new user
        val hashMapUser = constructHashMapForNewUser(user);

        firestoreDatabase.collection(usersCollection).add(hashMapUser)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    fun insertSpotIntoGoogleFireStore(spot: Spot) {
        val hashMapSpot = constructHashMapForNewSpot(spot)

        firestoreDatabase.collection(spotsCollection).add(hashMapSpot)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }.addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    // Get all spots in the Spots Collection
    fun getAllSpots(): ArrayList<Spot> {
        firestoreDatabase.collection(spotsCollection).get().addOnSuccessListener { result ->
            for (document in result) {
                Log.d(TAG, "${document.id} => ${document.toObject(Spot::class.java)}")
                val latitude = document.getString("latitude")
                val longitude = document.getString("longitude")
                val spotName = document.getString("name")
                val spotCity = document.getString("spotCity")
                val spotState = document.getString("spotState")
                listOfSpots.add(Spot(LatLng(latitude!!.toDouble(),longitude!!.toDouble()), spotName, spotCity, spotState))
            }
        }.addOnFailureListener { exception ->
            Log.w(TAG, "Error adding document", exception)
        }
        return listOfSpots
    }
}