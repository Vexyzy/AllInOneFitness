package com.example.all_in_one_fitness.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "steps_table")
data class Steps(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val year: Int,
    val month: String,
    val day: Int,
    val totalSteps: Float
)