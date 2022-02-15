package com.example.googlefirebase.signin_registration_feature.data.local.dao
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.googlefirebase.signin_registration_feature.data.local.entity.RegisteredUserTuple
import com.example.googlefirebase.signin_registration_feature.data.local.entity.UserEntity
import com.example.googlefirebase.signin_registration_feature.domain.models.User

@Dao
interface UserDao {

    @Query("SELECT username_column, user_password_column FROM user_table WHERE username_column = :username_column AND user_password_column = :user_password_column")
    suspend fun checkIfUserExists(
        username_column: String?,
        user_password_column: String?
    ): RegisteredUserTuple?

    @Query("SELECT * from user_table WHERE username_column = :username_column")
     fun getUser(username_column: String?): UserEntity?

    @Insert
    suspend fun insertUserIntoLocalDatabase(userEntity: UserEntity)
}