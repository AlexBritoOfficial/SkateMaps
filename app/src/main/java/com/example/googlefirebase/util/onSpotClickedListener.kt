package com.example.googlefirebase.util

import com.example.googlefirebase.signin_registration_feature.domain.models.Spot

interface onSpotClickedListener {
    fun onItemClicked(spot: Spot?)
}