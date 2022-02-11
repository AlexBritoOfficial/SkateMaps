package com.example.googlefirebase.signin_registration_feature.domain.repositories

import android.content.Context
import com.example.googlefirebase.signin_registration_feature.domain.models.Spot
import com.example.googlefirebase.signin_registration_feature.domain.models.User

class RemoteRepository(context: Context) {

    val context = context

    var googleFireStoreRepository: GoogleFireStore = GoogleFireStore(this.context)

    suspend fun insertUserIntoGoogleFireStore(user: User) {
        googleFireStoreRepository.insertUserIntoGoogleFireStore(user)
    }

    suspend fun insertSpotIntoGoogleFireStore(spot: Spot) {
        googleFireStoreRepository.insertSpotIntoGoogleFireStore(spot)
    }

    suspend fun getAllSpots(): ArrayList<Spot> {
        return googleFireStoreRepository.getAllSpots()
    }

//    suspend fun getAllSpots() {
//        return googleFireStoreRepository.getAllSpots()
//    }
}