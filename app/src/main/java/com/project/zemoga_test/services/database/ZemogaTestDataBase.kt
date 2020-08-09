package com.project.zemoga_test.services.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.project.zemoga_test.services.database.post.PostDao
import com.project.zemoga_test.services.database.post.entities.PostEntity

@Database(entities = [PostEntity::class], version = 1)
abstract class ZemogaTestDataBase : RoomDatabase() {

    abstract fun postDao(): PostDao

    companion object {
        fun getInstance(context: Context): ZemogaTestDataBase {
            return Room.databaseBuilder(
                context,
                ZemogaTestDataBase::class.java, "project-database"
            ).build()

        }
    }
}