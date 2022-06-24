package com.example.mobiletestzemoga.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobiletestzemoga.databinding.FragmentFavoritesBinding
import com.example.mobiletestzemoga.ui.PostAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private lateinit var adapter: PostAdapter
    private val favoriteViewModel: FavoriteViewModel by viewModel()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initRecyclerView()
        favoriteViewModel.posts.observe(viewLifecycleOwner) {
            adapter.collection = it
        }

        favoriteViewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        favoriteViewModel.wasDeleted.observe(viewLifecycleOwner) {
            if(it){
                favoriteViewModel.getFavoritePosts()
            }
        }

        return root
    }

    override fun onResume() {
        super.onResume()
        favoriteViewModel.getFavoritePosts()
    }

    private fun initRecyclerView() {
        adapter = PostAdapter(true)
        binding.recyclerViewFavorites.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewFavorites.adapter = adapter
        adapter.clickListener = {
            favoriteViewModel.removeFavorite(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}