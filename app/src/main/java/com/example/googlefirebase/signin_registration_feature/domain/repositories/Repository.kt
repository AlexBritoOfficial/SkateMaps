package com.example.googlefirebase.signin_registration_feature.domain.repositories

import android.content.Context
import com.example.googlefirebase.signin_registration_feature.data.local.entity.RegisteredUserTuple
import com.example.googlefirebase.signin_registration_feature.domain.models.User

class Repository(context: Context) {

    // Member  Fields
    private val context = context
    private lateinit var remoteRepository: RemoteRepository
    private lateinit var localRepository: LocalRepository

    init {
        remoteRepository = RemoteRepository(this.context)
        localRepository = LocalRepository(this.context)
    }

    suspend fun insertUserIntoGoogleFireStore(user: User) {
        remoteRepository.insertUserIntoGoogleFireStore(user)
    }

    suspend fun insertUserIntoCache(user: User) {
        localRepository.insertUserIntoCache(user)
    }

    suspend fun checkIfUserExists(
        userName: String?,
        userPassword: String?
    ): RegisteredUserTuple {
        return localRepository.checkIfUserExists(userName, userPassword)
    }
}

