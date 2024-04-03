package com.example.all_in_one_fitness.data_fitness

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Fitness::class], version = 1, exportSchema = false)
abstract class FitnessDatabase : RoomDatabase() {

    abstract fun fitnessDao(): FitnessDao

    companion object{

        @Volatile
        private var INSTANCE: FitnessDatabase? = null

        fun getDatabase(context: Context): FitnessDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FitnessDatabase::class.java,
                    "fitness_table"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}