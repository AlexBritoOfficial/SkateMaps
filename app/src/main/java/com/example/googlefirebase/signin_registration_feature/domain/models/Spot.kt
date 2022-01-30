package com.example.googlefirebase.signin_registration_feature.domain.models

import com.google.android.gms.maps.model.LatLng

data class Spot (val spotName: String,
                 val spotCity: String,
                 val spotState: String,
                 val spotLatLng: LatLng)    {

}