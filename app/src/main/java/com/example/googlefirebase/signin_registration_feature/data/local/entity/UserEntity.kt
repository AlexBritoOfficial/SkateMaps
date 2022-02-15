package com.example.googlefirebase.signin_registration_feature.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.googlefirebase.signin_registration_feature.domain.models.User


@Entity(tableName = "user_table")
data class UserEntity(

    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "username_column") var userName: String?,
    @ColumnInfo(name = "firstname_column") var firstName: String?,
    @ColumnInfo(name = "lastname_column") var lastName: String?,
    @ColumnInfo(name = "email_column") var email: String?,
    @ColumnInfo(name = "user_password_column") var userPassword: String?,
    @ColumnInfo(name = "user_confirmed_password_column") var confirmedPassword: String?
)
{
    fun toUser(): User {
        return User(
            userName = userName,
            firstName = firstName,
            lastName = lastName,
            email = email,
            userPassword = userPassword,
            confirmedPassword = confirmedPassword
        )
    }
}