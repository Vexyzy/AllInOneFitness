package com.example.all_in_one_fitness.data_fitness

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "fitness_table")
class Fitness(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val type: String,
    val title: String,
    val subTitle: String,
    val link: String
)