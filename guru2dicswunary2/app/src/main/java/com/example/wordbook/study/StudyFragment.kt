package com.example.wordbook.study

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.example.wordbook.R
import com.example.wordbook.databinding.FragmentStudyBinding
import com.example.wordbook.databinding.FragmentTestBinding
import com.example.wordbook.test.TestViewModel
import com.example.wordbook.vocalist.VocaListFragment

class StudyFragment : Fragment() {

    companion object {
        fun newInstance() = StudyFragment()
    }

    private lateinit var viewModel: StudyViewModel
    private lateinit var binding: FragmentStudyBinding
    private lateinit var backPressCallback: OnBackPressedCallback

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_study, container, false)
        viewModel = ViewModelProvider(this).get(StudyViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.error.observe(viewLifecycleOwner) {
            if (it == true) {
                Toast.makeText(requireContext(), R.string.msg_study_error, Toast.LENGTH_SHORT).show()
                destroy()
            }
        }

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        requireActivity().onBackPressedDispatcher.addCallback(this, getBackPressCallback())
    }

    override fun onDetach() {
        super.onDetach()

        getBackPressCallback().remove()
    }

    private fun getBackPressCallback(): OnBackPressedCallback {
        if (!::backPressCallback.isInitialized) {
            backPressCallback = object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    destroy()
                }
            }
        }
        return backPressCallback
    }

    private fun destroy() {
        parentFragmentManager.popBackStack()
    }
}