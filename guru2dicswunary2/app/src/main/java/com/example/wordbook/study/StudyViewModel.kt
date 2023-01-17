package com.example.wordbook.study

import android.app.Application
import androidx.lifecycle.*
import com.example.wordbook.database.Word
import com.example.wordbook.database.getDatabase
import com.example.wordbook.repository.WordRepository
import kotlinx.coroutines.*

class StudyViewModel(application: Application): AndroidViewModel(application) {
    enum class StudyType {
        BOTH,
        ONLY_ENGLISH,
        ONLY_KOREAN
    }

    private val repository = WordRepository(getDatabase(application))

    private lateinit var words: List<Word>
    private var idx = 0

    private val _studyType = MutableLiveData(StudyType.BOTH)
    val studyType: LiveData<StudyType>
        get() = _studyType

    private val _word = MutableLiveData<Word?>()
    val word : LiveData<Word?>
        get() = _word

    private val _error = MutableLiveData(false)
    val error: LiveData<Boolean>
        get() = _error

    init {
        loadWords()
    }

    private fun loadWords() {
        viewModelScope.launch {
            words = repository.getWordList().shuffled()
            if (words.isEmpty()) {
                // this condition will be not happened.
                _error.value = true
                return@launch
            }
            _word.value = words[idx]
        }
    }

    fun loadNextWord() {
        idx = if (idx < words.size - 1) idx + 1 else 0

        _word.value = words[idx]
    }

    fun loadPreviousWord() {
        idx = if (idx > 0) idx - 1 else words.size - 1

        _word.value = words[idx]
    }

    fun setNextStudyType() {
        _studyType.value = when (_studyType.value) {
            StudyType.BOTH -> StudyType.ONLY_ENGLISH
            StudyType.ONLY_ENGLISH -> StudyType.ONLY_KOREAN
            StudyType.ONLY_KOREAN -> StudyType.BOTH
            else -> StudyType.BOTH
        }
    }
}