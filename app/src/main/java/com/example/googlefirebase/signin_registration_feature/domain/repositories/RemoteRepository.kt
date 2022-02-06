package com.example.googlefirebase.signin_registration_feature.domain.repositories

import android.content.Context
import com.example.googlefirebase.signin_registration_feature.domain.models.Spot
import com.example.googlefirebase.signin_registration_feature.domain.models.User

class RemoteRepository(context: Context) {

    val context = context

    private lateinit var googleFireStoreRepository: GoogleFireStore

    init {
        googleFireStoreRepository = GoogleFireStore(this.context)
    }

   suspend fun insertUserIntoGoogleFireStore(user: User){
        googleFireStoreRepository.insertUserIntoGoogleFireStore(user)
    }

    suspend fun insertSpotIntoGoogleFireStore(spot: Spot){
        googleFireStoreRepository.insertSpotIntoGoogleFireStore(spot)
    }

}