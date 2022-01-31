package com.example.googlefirebase.signin_registration_feature.data.local.entity

import androidx.room.ColumnInfo

data class RegisteredUserTuple(
    @ColumnInfo(name = "username_column") var userName: String?,
    @ColumnInfo(name = "user_password_column") var  userPassword: String?
)
