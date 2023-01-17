package com.example.wordbook.vocalist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wordbook.R
import com.example.wordbook.database.Word
import com.example.wordbook.databinding.FragmentVocaListBinding
import com.example.wordbook.edit.EditVocaFragment
import com.example.wordbook.register.RegisterVocaFragment

class VocaListFragment : Fragment() {

    companion object {
        fun newInstance() = VocaListFragment()
    }

    private lateinit var viewModel: VocaListViewModel
    private lateinit var binding: FragmentVocaListBinding
    private lateinit var adapter: VocaListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_voca_list, container, false)
        viewModel = ViewModelProvider(this).get(VocaListViewModel::class.java)

        binding.viewModel = viewModel

        binding.list.layoutManager = LinearLayoutManager(requireContext())
        adapter = VocaListAdapter(::moveToEditVoca)
        binding.list.adapter = adapter

        binding.add.setOnClickListener {
            moveToRegisterVoca()
        }

        viewModel.vocas.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        return binding.root
    }

    private fun moveToEditVoca(word: Word) {
        parentFragmentManager.beginTransaction()
            .replace(
                VocaListBaseFragment.VOCA_LIST_FRAGMENT_CONTAINER_ID,
                EditVocaFragment.newInstance(word.id)
            )
            .setReorderingAllowed(true)
            .addToBackStack(null)
            .commit()
    }

    private fun moveToRegisterVoca() {
        parentFragmentManager.beginTransaction()
            .replace(
                VocaListBaseFragment.VOCA_LIST_FRAGMENT_CONTAINER_ID,
                RegisterVocaFragment.newInstance()
            )
            .setReorderingAllowed(true)
            .addToBackStack(null)
            .commit()
    }
}