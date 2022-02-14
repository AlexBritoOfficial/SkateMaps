package com.example.googlefirebase.signin_registration_feature.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.googlefirebase.signin_registration_feature.domain.models.Spot
import com.example.googlefirebase.signin_registration_feature.domain.repositories.Repository
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomePageViewModel(context: Context) : ViewModel() {

    val context = context
    private var repository: Repository
    private var listOfSpots: ArrayList<Spot> = ArrayList()
    private var spotsCollection = "Spots"

    // Create LiveData Object for Spot
    private var mutableLiveSpots: MutableLiveData<ArrayList<Spot>> =
        MutableLiveData<ArrayList<Spot>>()

    init {
        Log.i("HomePageViewModel", "HomePageViewModel created!")
        this.repository = Repository(this.context)
        listenToSpots()
    }


    // This function will listen to new spots added into the Firestore database
    private fun listenToSpots() {

        viewModelScope.launch(Dispatchers.IO) {
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
    }

    /**
     * The ViewModel is destroyed when the associated fragment is detached, or when the activity is finished.
     * Right before the ViewModel is destroyed, the onCleared() callback is called to clean up the resources.
     **/

    override fun onCleared() {
        super.onCleared()
        Log.i("RegistrationViewModel", "RegistrationViewModel destroyed!")
    }

    fun insertSpotIntoGoogleFireStore(
        latitude: Double,
        longitude: Double,
        spotName: String,
        spotCity: String,
        spotState: String
    ) {
        val spot = Spot(LatLng(latitude, longitude), spotName, spotCity, spotState)

        viewModelScope.launch(Dispatchers.IO) {
            repository.insertSpotIntoGoogleFireStore(spot)
        }
    }

    internal var mutableLiveSpotsAccessor: MutableLiveData<ArrayList<Spot>>
        get() = mutableLiveSpots
        set(value) {
            mutableLiveSpots = value
        }
}

