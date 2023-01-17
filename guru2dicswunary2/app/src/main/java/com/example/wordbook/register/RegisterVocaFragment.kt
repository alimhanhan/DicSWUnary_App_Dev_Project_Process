package com.example.wordbook.register

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.wordbook.R
import com.example.wordbook.database.Word
import com.example.wordbook.databinding.FragmentRegisterVocaBinding

class RegisterVocaFragment : Fragment() {

    companion object {
        fun newInstance() = RegisterVocaFragment()
    }

    private lateinit var binding: FragmentRegisterVocaBinding
    private lateinit var viewModel: RegisterVocaViewModel
    private lateinit var backPressCallback: OnBackPressedCallback

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_register_voca, container, false)
        viewModel = ViewModelProvider(this).get(RegisterVocaViewModel::class.java)

        binding.confirm.setOnClickListener {
            val english = binding.englishInput.text.toString()
            val means = binding.meansInput.text.toString()

            viewModel.registerWord(Word.make(english, means))
            destroy()
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