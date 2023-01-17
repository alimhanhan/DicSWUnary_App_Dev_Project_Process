package com.example.wordbook.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "words")
data class Word(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val english: String,
    val means: String,
    val timestamp: String
) {
    companion object {
        fun make(english: String, means: String): Word {
            return Word(0, english, means, System.currentTimeMillis().toString())
        }

        fun make(id: Int, english: String, means: String): Word {
            return Word(id, english, means, System.currentTimeMillis().toString())
        }
    }
}