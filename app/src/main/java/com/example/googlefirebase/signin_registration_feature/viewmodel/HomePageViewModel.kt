package com.example.googlefirebase.signin_registration_feature.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.googlefirebase.signin_registration_feature.domain.models.Spot
import com.example.googlefirebase.signin_registration_feature.domain.repositories.Repository
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomePageViewModel(context: Context): ViewModel() {

    val context = context
    private lateinit var repository: Repository

    init {
        Log.i("HomePageViewModel", "HomePageViewModel created!")
        this.repository = Repository(this.context)
    }

    /**
     * The ViewModel is destroyed when the associated fragment is detached, or when the activity is finished.
     * Right before the ViewModel is destroyed, the onCleared() callback is called to clean up the resources.
     **/

    override fun onCleared() {
        super.onCleared()
        Log.i("RegistrationViewModel", "RegistrationViewModel destroyed!")
    }

     fun insertSpotIntoGoogleFireStore(latitude: Double, longitude: Double, spotName: String, spotCity: String, spotState: String){
        val spot = Spot(LatLng(latitude,longitude), spotName, spotCity, spotState)

        viewModelScope.launch(Dispatchers.IO){
            repository.insertSpotIntoGoogleFireStore(spot)
        }
    }
}