package com.example.googlefirebase.signin_registration_feature.domain.models

import com.google.android.gms.maps.model.LatLng

data class Spot (val spotLatLng: LatLng, var spotName: String, var spotCity: String, var spotState: String) {

}