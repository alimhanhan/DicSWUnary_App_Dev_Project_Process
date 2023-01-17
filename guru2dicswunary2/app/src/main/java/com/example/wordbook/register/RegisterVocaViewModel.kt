package com.example.wordbook.register

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.wordbook.database.Word
import com.example.wordbook.database.getDatabase
import com.example.wordbook.repository.WordRepository
import kotlinx.coroutines.launch

class RegisterVocaViewModel(application: Application): AndroidViewModel(application) {
    private val repository = WordRepository(getDatabase(application))

    fun registerWord(word: Word) {
        viewModelScope.launch {
            repository.save(word)
        }
    }
}