package com.example.googlefirebase.signin_registration_feature.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.googlefirebase.signin_registration_feature.data.local.entity.RegisteredUserTuple
import com.example.googlefirebase.signin_registration_feature.domain.models.User
import com.example.googlefirebase.signin_registration_feature.domain.repositories.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegistrationViewModel(context: Context) : ViewModel() {

    val context = context

    private lateinit var repository: Repository
    private var registeredUserTuple: RegisteredUserTuple? = null

    init {
        Log.i("RegistrationViewModel", "RegistrationViewModel created!")
        repository = Repository(this.context)
    }


    /**
     * The ViewModel is destroyed when the associated fragment is detached, or when the activity is finished.
     * Right before the ViewModel is destroyed, the onCleared() callback is called to clean up the resources.
     **/

    override fun onCleared() {
        super.onCleared()
        Log.i("RegistrationViewModel", "RegistrationViewModel destroyed!")
    }

    fun insertUserIntoRemoteAndLocalDataCenters(
        userName: String,
        firstName: String,
        lastName: String,
        password: String,
        confirmedPassword: String
    ) {
        val user =
            User(userName, firstName, lastName, password, confirmedPassword)

        viewModelScope.launch(Dispatchers.IO) {
            repository.insertUserIntoGoogleFireStore(user)
            repository.insertUserIntoCache(user)
        }
    }

    suspend fun checkIfUserExists(userName: String?, userPassword: String?): RegisteredUserTuple? {

        withContext(Dispatchers.IO) {
            registeredUserTuple = repository.checkIfUserExists(userName, userPassword)
        }
        return registeredUserTuple
    }


}