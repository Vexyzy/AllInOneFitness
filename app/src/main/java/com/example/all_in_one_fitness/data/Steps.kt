package com.example.all_in_one_fitness.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "steps_table")
data class Steps(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val localDate: String,
    val totalSteps: Float
)