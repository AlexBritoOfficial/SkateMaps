package com.example.googlefirebase.signin_registration_feature.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.googlefirebase.signin_registration_feature.domain.models.Spot
import com.example.googlefirebase.signin_registration_feature.domain.repositories.Repository
import com.google.android.gms.maps.model.LatLng

class SpotsViewModel(context: Context): ViewModel() {

    /** Class Members **/
    private val TAG = "SpotsViewModel"
    private val context = context
    private var listOfSpots: ArrayList<Spot> = ArrayList()
    private var spotsCollection = "Spots"
    private var repository: Repository

    // Create LiveData Object for Spot
    private var mutableLiveSpots: MutableLiveData<ArrayList<Spot>> =
        MutableLiveData<ArrayList<Spot>>()

    init {
        repository = Repository(this.context)
        listenToSpots()
    }

    override fun onCleared() {
        super.onCleared()
        Log.i(TAG, "$TAG destroyed!")

    }

    // This function will listen to new spots added into the Firestore database
    private fun listenToSpots() {
        repository.remoteRepository.googleFireStoreRepository.firestoreDatabase.collection(
            spotsCollection
        ).addSnapshotListener { snapshot, error ->

            if (error != null) {
                return@addSnapshotListener
            }

            if (snapshot != null) {
                // Create list to store spots
                val listOfSpots = ArrayList<Spot>()
                val documents = snapshot.documents
                for (document in documents) {

                    val latitude = document.getString("latitude")
                    val longitude = document.getString("longitude")
                    val spotName = document.getString("name")
                    val spotCity = document.getString("city")
                    val spotState = document.getString("state")
                    listOfSpots.add(
                        Spot(
                            LatLng(latitude!!.toDouble(), longitude!!.toDouble()),
                            spotName,
                            spotCity,
                            spotState
                        )
                    )
                }

                mutableLiveSpots.value = listOfSpots
            }

        }
    }

    internal var mutableLiveSpotsAccessor: MutableLiveData<ArrayList<Spot>>
        get() = mutableLiveSpots
        set(value) {
            mutableLiveSpots = value
        }
}