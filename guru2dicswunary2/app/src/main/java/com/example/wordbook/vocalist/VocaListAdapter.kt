package com.example.wordbook.vocalist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.wordbook.R
import com.example.wordbook.database.Word
import com.example.wordbook.databinding.ViewholderVocaBinding

class VocaListAdapter(val onMoveToEditVoca: (word: Word) -> Unit) : ListAdapter<Word, VocaListAdapter.VocaViewHolder>(VocaDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VocaViewHolder {
        return VocaViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: VocaViewHolder, position: Int) {
        holder.binding.word = getItem(position)
        holder.binding.onClick = VocaClickListener(onMoveToEditVoca)
    }

    class VocaDiffCallback : DiffUtil.ItemCallback<Word>() {
        override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem == newItem
        }

    }

    class VocaViewHolder private constructor(val binding: ViewholderVocaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): VocaViewHolder {
                return VocaViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.viewholder_voca,
                        parent,
                        false
                    )
                )
            }
        }
    }

    class VocaClickListener(val onMoveToEditVoca: (word: Word) -> Unit) {
        fun onVocaClick(word: Word) = onMoveToEditVoca(word)
    }
}