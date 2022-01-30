package com.example.googlefirebase.signin_registration_feature.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.android.gms.maps.model.LatLng

@Entity(tableName = "spots_table")
data class SpotEntity(@ColumnInfo(name = "spot_name") val spotName: String,
                      @ColumnInfo(name = "spot_city") val spotCity: String,
                      @ColumnInfo(name = "spot_state") val spotState: String)
