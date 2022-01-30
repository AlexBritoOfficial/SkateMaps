package com.example.googlefirebase.signin_registration_feature.domain.repositories

import android.content.Context
import com.example.googlefirebase.signin_registration_feature.data.local.dao.UserDao
import com.example.googlefirebase.signin_registration_feature.data.local.database.AppLocalDatabase
import com.example.googlefirebase.signin_registration_feature.data.local.entity.UserEntity
import com.example.googlefirebase.signin_registration_feature.domain.models.User


class LocalRepository(context: Context) {


    private val context = context

    private val database = AppLocalDatabase.getDatabase(this.context)
    private val userDao = database.userDAO()

    suspend fun getUser(userName: String, userPassword: String) {
        // userDao.checkIfUserExists(userName, userPassword)
    }

    suspend fun insertUserIntoCache(user: User) {
        val (userName, firstName, lastName, userPassword, confirmedPassword) = user
        val userEntity =
            UserEntity(0, userName, firstName, lastName, userPassword, confirmedPassword)
        userDao.insertUserIntoLocalDatabase(userEntity)
    }

}