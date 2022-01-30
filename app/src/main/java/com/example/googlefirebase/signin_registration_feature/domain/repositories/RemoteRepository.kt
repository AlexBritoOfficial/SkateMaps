package com.example.googlefirebase.signin_registration_feature.domain.repositories

import android.content.Context
import com.example.googlefirebase.signin_registration_feature.domain.models.User

class RemoteRepository(context: Context) {

    val context = context

    private lateinit var googleFireStoreRegistrationRepository: GoogleFireStoreRegistrationRepository

    init {
        googleFireStoreRegistrationRepository = GoogleFireStoreRegistrationRepository(this.context)
    }

   suspend fun insertUserIntoGoogleFireStore(user: User){
        googleFireStoreRegistrationRepository.insertUserIntoGoogleFireStore(user)
    }

}