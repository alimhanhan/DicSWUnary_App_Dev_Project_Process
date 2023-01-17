package com.example.wordbook.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Word::class], version = 1)
abstract class WordDatabase: RoomDatabase() {
    abstract val wordDao: WordDao
}

private const val DATABASE_NAME = "words.db"
private lateinit var INSTANCE: WordDatabase

fun getDatabase(context: Context): WordDatabase {
    synchronized(WordDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                WordDatabase::class.java, DATABASE_NAME).build()
        }
    }

    return INSTANCE
}