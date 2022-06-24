package com.example.mobiletestzemoga.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mobiletestzemoga.data.network.CommentResponse
import com.example.mobiletestzemoga.databinding.ItemCommentBinding
import kotlin.properties.Delegates

class CommentAdapter : RecyclerView.Adapter<CommentAdapter.ViewHolder>() {
    internal var collection: List<CommentResponse> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(collection[position])
    }

    override fun getItemCount(): Int {
        return collection.size
    }

    class ViewHolder(private val binding: ItemCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(commentResponse: CommentResponse) {
            binding.textViewName.text = commentResponse.name
            binding.textViewBody.text = commentResponse.body
            binding.textViewEmail.text = commentResponse.email
        }
    }
}