package com.example.googlefirebase.signin_registration_feature.domain.repositories

import android.content.Context
import com.example.googlefirebase.signin_registration_feature.data.local.database.AppLocalDatabase
import com.example.googlefirebase.signin_registration_feature.data.local.entity.RegisteredUserTuple
import com.example.googlefirebase.signin_registration_feature.data.local.entity.UserEntity
import com.example.googlefirebase.signin_registration_feature.domain.models.User


class LocalRepository(context: Context) {

    /** CLASS PROPERTIES **/
    private val context = context
    private val database = AppLocalDatabase.getDatabase(this.context)
    private val userDao = database.userDAO()

    suspend fun checkIfUserExists(userName: String?, userPassword: String?): RegisteredUserTuple {

        val registeredUserTuple = userDao.checkIfUserExists(userName, userPassword)
        return registeredUserTuple
    }

    suspend fun insertUserIntoCache(user: User) {
        val (userName, firstName, lastName, userPassword, confirmedPassword) = user
        val userEntity =
            UserEntity(0, userName, firstName, lastName, userPassword, confirmedPassword)
        userDao.insertUserIntoLocalDatabase(userEntity)
    }

}