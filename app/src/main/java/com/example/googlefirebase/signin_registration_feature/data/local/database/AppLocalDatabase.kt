package com.example.googlefirebase.signin_registration_feature.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.googlefirebase.signin_registration_feature.data.local.dao.UserDao
import com.example.googlefirebase.signin_registration_feature.data.local.entity.UserEntity

@Database(entities = [UserEntity::class], version = 3)
abstract class AppLocalDatabase : RoomDatabase() {

    abstract fun userDAO(): UserDao

    companion object {

        @Volatile
        private var INSTANCE: AppLocalDatabase? = null


        fun getDatabase(context: Context): AppLocalDatabase {
            val tempInstance = INSTANCE

            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppLocalDatabase::class.java,
                    "AppLocalDatabase"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}