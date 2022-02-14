package com.example.googlefirebase.signin_registration_feature.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.googlefirebase.ui.fragments.SpotsFragment

class SpotsViewModelFactory(context: Context) : ViewModelProvider.Factory {

    private val context = context

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SpotsViewModel::class.java)) {
            return SpotsViewModel(this.context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}