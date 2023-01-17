package com.example.wordbook.vocalist

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wordbook.database.getDatabase
import com.example.wordbook.repository.WordRepository

class VocaListViewModel(application: Application): AndroidViewModel(application) {
    private val repository = WordRepository(getDatabase(application))

    val vocas = repository.getWordListByLiveData()
}