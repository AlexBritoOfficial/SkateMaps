package com.example.googlefirebase.signin_registration_feature.domain.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.googlefirebase.signin_registration_feature.data.local.entity.RegisteredUserTuple
import com.example.googlefirebase.signin_registration_feature.domain.models.Spot
import com.example.googlefirebase.signin_registration_feature.domain.models.User

class Repository(context: Context) {

    // Member  Fields
    private val context = context
    var remoteRepository: RemoteRepository
    var localRepository = LocalRepository(this.context)

    init {
        remoteRepository = RemoteRepository(this.context)
    }

    suspend fun insertUserIntoGoogleFireStore(user: User) {
        remoteRepository.insertUserIntoGoogleFireStore(user)
    }

    suspend fun insertSpotIntoGoogleFireStore(spot: Spot) {
        remoteRepository.insertSpotIntoGoogleFireStore(spot)
    }

    suspend fun insertUserIntoCache(user: User) {
        localRepository.insertUserIntoCache(user)
    }

//     suspend fun checkIfUserExists(userName: String?, userPassword: String?): RegisteredUserTuple?  {
//        return localRepository.checkIfUserExists(userName, userPassword)
//    }

    suspend fun checkIfUserExists(userName: String?, userPassword: String?): RegisteredUserTuple? {
        return localRepository.checkIfUserExists(userName, userPassword)
    }

    suspend fun getAllSpots(): ArrayList<Spot> {
        return remoteRepository.getAllSpots()
    }

//    suspend fun getAllSpots(){
//        return remoteRepository.getAllSpots()
//    }
}

