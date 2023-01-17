package com.example.wordbook.test

import android.app.Application
import androidx.lifecycle.*
import com.example.wordbook.database.Word
import com.example.wordbook.database.getDatabase
import com.example.wordbook.repository.WordRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TestViewModel(application: Application): AndroidViewModel(application) {
    data class TestUnit(val question: Word, val candidates: List<Word>)

    enum class ResultState {
        NONE,
        CORRECT,
        WRONG
    }

    companion object {
        private val DELAY_TIME_SHOWING_NEXT_TEST = 800L
    }
    private val repository = WordRepository(getDatabase(application))

    private val _mTestUnit = MutableLiveData<TestUnit>()
    val mTestUnit: LiveData<TestUnit>
        get() = _mTestUnit

    private val _mResultState = MutableLiveData(ResultState.NONE)
    val mResultState: LiveData<ResultState>
        get() = _mResultState

    init {
        loadNextTestUnit()
    }

    private fun loadNextTestUnit() {
        viewModelScope.launch {
            val shuffledList = repository.getWordList().shuffled()
            _mTestUnit.value = TestUnit(shuffledList[0], shuffledList.subList(0, 4).shuffled())
            _mResultState.value = ResultState.NONE
        }
    }

    fun onClickCandidate(candidateIdx: Int) {
        when (isCorrect(candidateIdx)) {
            true -> {
                _mResultState.value = ResultState.CORRECT
                viewModelScope.launch {
                    delay(DELAY_TIME_SHOWING_NEXT_TEST)
                    loadNextTestUnit()
                }
            }
            false -> {
                _mResultState.value = ResultState.WRONG
            }
        }
    }

    private fun isCorrect(candidateIdx: Int): Boolean {
        _mTestUnit.value?.let {
            return it.candidates[candidateIdx].id == it.question.id
        }

        return false
    }
}