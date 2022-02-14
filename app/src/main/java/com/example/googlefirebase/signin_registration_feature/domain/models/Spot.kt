package com.example.googlefirebase.signin_registration_feature.domain.models

import android.os.Parcel
import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng

class Spot (var spotLatLng: LatLng? = null, var name: String? = null, var city: String? = null, var state: String? = null): Parcelable{

    constructor(parcel: Parcel) : this(
        parcel.readParcelable(LatLng::class.java.classLoader),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(spotLatLng, flags)
        parcel.writeString(name)
        parcel.writeString(city)
        parcel.writeString(state)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Spot> {
        override fun createFromParcel(parcel: Parcel): Spot {
            return Spot(parcel)
        }

        override fun newArray(size: Int): Array<Spot?> {
            return arrayOfNulls(size)
        }
    }

}