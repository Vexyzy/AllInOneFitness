package com.example.roomapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.all_in_one_fitness.data.Steps

@Database(entities = [Steps::class], version = 1, exportSchema = false)
abstract class StepsDatabase : RoomDatabase() {

    abstract fun userDao(): StepsDao

    companion object {
        @Volatile
        private var INSTANCE: StepsDatabase? = null

        fun getDatabase(context: Context): StepsDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StepsDatabase::class.java,
                    "steps_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}