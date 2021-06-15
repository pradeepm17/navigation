package com.learntodroid.bottomnavigationtutorial

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location")
data class Location(

    @PrimaryKey val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residence: List<String>?,
    var page: Int?
)