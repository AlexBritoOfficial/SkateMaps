package com.example.googlefirebase.signin_registration_feature.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress
class HomePageViewModelFactory(private val context: Context): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HomePageViewModel::class.java)){
            return HomePageViewModel(this.context) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}