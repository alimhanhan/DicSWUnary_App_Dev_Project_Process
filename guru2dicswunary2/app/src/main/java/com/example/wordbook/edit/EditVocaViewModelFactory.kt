package com.example.wordbook.edit

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class EditVocaViewModelFactory(private val application: Application,
                               private val wordId: Int): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditVocaViewModel::class.java)) {
            return EditVocaViewModel(application, wordId) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}