package com.example.mobiletestzemoga.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mobiletestzemoga.data.network.PostResponse
import com.example.mobiletestzemoga.databinding.ItemPostBinding
import kotlin.properties.Delegates


class PostAdapter(private val isFavorite:Boolean = false) : RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    internal var collection: List<PostResponse> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }
    internal var clickListener: (PostResponse) -> Unit = { _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding,isFavorite)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(collection[position], clickListener)
    }

    override fun getItemCount(): Int {
        return collection.size
    }

    class ViewHolder(private val binding: ItemPostBinding, private val isFavorite: Boolean) : RecyclerView.ViewHolder(binding.root) {
        fun bind(postResponse: PostResponse, clickListener: (PostResponse) -> Unit){
            binding.textViewTitle.text = postResponse.title
            if(postResponse.isFavorite){
                binding.imageFavorite.visibility = View.VISIBLE
            }else {
                binding.imageFavorite.visibility = View.GONE
            }

            if(!isFavorite){
                binding.imageViewDelete.visibility= View.GONE
                itemView.setOnClickListener {
                    clickListener(postResponse)
                }
            }else {
                binding.imageViewDelete.visibility= View.VISIBLE
                binding.imageViewDelete.setOnClickListener {
                    clickListener(postResponse)
                }
            }

        }
    }
}
