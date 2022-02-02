package com.example.googlefirebase.signin_registration_feature.data.local.entity

import androidx.room.ColumnInfo

data class RegisteredUserTuple(
    @ColumnInfo(name = "username_column") var username_column: String?,
    @ColumnInfo(name = "user_password_column") var  user_password_column: String?
)
