package com.example.googlefirebase.signin_registration_feature.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.googlefirebase.signin_registration_feature.data.local.entity.RegisteredUserTuple
import com.example.googlefirebase.signin_registration_feature.data.local.entity.UserEntity

@Dao
interface UserDao {

    @Query("SELECT username_column, user_password_column FROM user_table WHERE username_column == :userName AND user_password_column == :userPassword")
    suspend fun checkIfUserExists(userName: String?, userPassword: String?): RegisteredUserTuple

    @Insert
    suspend fun insertUserIntoLocalDatabase(userEntity: UserEntity)
}