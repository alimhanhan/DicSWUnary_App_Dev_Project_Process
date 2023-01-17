package com.example.wordbook.edit

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.wordbook.R
import com.example.wordbook.databinding.FragmentEditVocaBinding
import kotlinx.coroutines.launch

private const val ARG_VOCA_ID = "voca_id"

class EditVocaFragment : Fragment() {

    companion object {
        fun newInstance(vocaId: Int): EditVocaFragment {
            val fragment = EditVocaFragment()
            val args = Bundle()
            args.putInt(ARG_VOCA_ID, vocaId)
            fragment.arguments = args

            return fragment
        }
    }

    private lateinit var binding: FragmentEditVocaBinding
    private lateinit var viewModel: EditVocaViewModel
    private lateinit var backPressCallback: OnBackPressedCallback

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_edit_voca, container, false)

        val mWordId = arguments?.let {
            it.getInt(ARG_VOCA_ID)
        } ?: -1

        Log.d("Yebon", "wordId : $mWordId")

        val viewModelFactory = EditVocaViewModelFactory(requireActivity().application, mWordId)
        viewModel = ViewModelProvider(this, viewModelFactory).get(EditVocaViewModel::class.java)

        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is EditVocaViewModelState.Ready -> binding.word = it.word
//                is EditVocaViewModelState.Loading -> Toast.makeText(this, "loading", Toast.LENGTH_SHORT).show()
            }
        }

        binding.confirm.setOnClickListener {
            val english = binding.englishInput.text.toString()
            val means = binding.meansInput.text.toString()

            lifecycleScope.launch {
                viewModel.updateWord(english, means)
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