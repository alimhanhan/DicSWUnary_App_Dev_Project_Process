package com.example.wordbook.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface WordDao {
    @Insert
    suspend fun insert(word: Word): Long

    @Update
    suspend fun update(word: Word): Int

    @Query("select * from words")
    fun selectAllWithLiveData(): LiveData<List<Word>>

    @Query("select * from words")
    suspend fun selectAll(): List<Word>

    @Query("select * from words WHERE id = :id")
    suspend fun findById(id: Int): Word

    @Query("select count(*) from words")
    suspend fun getCount(): Int
}