package com.example.wordbook.util

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.wordbook.R
import com.example.wordbook.database.Word
import com.example.wordbook.study.StudyViewModel
import com.example.wordbook.test.TestViewModel

@BindingAdapter("english")
fun TextView.setEnglish(word: Word?) {
    text = word?.let {
        it.english
    } ?: ""
}

@BindingAdapter("means")
fun TextView.setMeans(word: Word?) {
    text = word?.let {
        it.means
    } ?: ""
}

@BindingAdapter("studyEnglishVisibility")
fun TextView.setStudyEnglishVisibility(studyType: StudyViewModel.StudyType) {
    visibility = when (studyType) {
        StudyViewModel.StudyType.BOTH -> View.VISIBLE
        StudyViewModel.StudyType.ONLY_ENGLISH -> View.VISIBLE
        StudyViewModel.StudyType.ONLY_KOREAN -> View.GONE
    }
}

@BindingAdapter("studyMeansVisibility")
fun TextView.setStudyMeansVisibility(studyType: StudyViewModel.StudyType) {
    visibility = when (studyType) {
        StudyViewModel.StudyType.BOTH -> View.VISIBLE
        StudyViewModel.StudyType.ONLY_ENGLISH -> View.GONE
        StudyViewModel.StudyType.ONLY_KOREAN -> View.VISIBLE
    }
}

@BindingAdapter("studyTypeButtonText")
fun Button.setStudyTypeButtonText(studyType: StudyViewModel.StudyType) {
    text = context.getString(
        when (studyType) {
            StudyViewModel.StudyType.BOTH -> R.string.title_both
            StudyViewModel.StudyType.ONLY_ENGLISH -> R.string.title_only_english
            StudyViewModel.StudyType.ONLY_KOREAN -> R.string.title_only_korean
        }
    )
}

@BindingAdapter("question")
fun TextView.setQuestionEnglish(item: TestViewModel.TestUnit?) {
    text = item?.let {
        it.question.english
    } ?: ""
}

@BindingAdapter("resultState")
fun TextView.setResultState(resultState: TestViewModel.ResultState?) {
    text = if (resultState != null) {
        context.getString(
            when (resultState) {
                TestViewModel.ResultState.NONE -> R.string.msg_none
                TestViewModel.ResultState.CORRECT -> R.string.msg_correct
                TestViewModel.ResultState.WRONG -> R.string.msg_wrong
            }
        )
    } else {
        context.getString(R.string.msg_none)
    }
}

@BindingAdapter("firstCandidate")
fun TextView.setFirstCandidate(item: TestViewModel.TestUnit?) {
    item?.let {
        text =  it.candidates[0].means
    }
}

@BindingAdapter("secondCandidate")
fun TextView.setSecondCandidate(item: TestViewModel.TestUnit?) {
    item?.let {
        text =  it.candidates[1].means
    }
}

@BindingAdapter("thirdCandidate")
fun TextView.setThirdCandidate(item: TestViewModel.TestUnit?) {
    item?.let {
        text =  it.candidates[2].means
    }
}

@BindingAdapter("fourthCandidate")
fun TextView.setFourthCandidate(item: TestViewModel.TestUnit?) {
    item?.let {
        text =  it.candidates[3].means
    }
}