package com.example.mobiletestzemoga.ui.post

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobiletestzemoga.core.POST_ID
import com.example.mobiletestzemoga.databinding.FragmentPostsBinding
import com.example.mobiletestzemoga.ui.PostAdapter
import com.example.mobiletestzemoga.ui.detail.DetailPostActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class PostFragment : Fragment() {

    private var _binding: FragmentPostsBinding? = null
    private lateinit var adapter: PostAdapter
    private val postViewModel: PostViewModel by viewModel()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPostsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initRecyclerView()
        postViewModel.posts.observe(viewLifecycleOwner) {
            adapter.collection = it
        }

        postViewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        postViewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.progressbar.visibility = View.VISIBLE
            } else {
                binding.progressbar.visibility = View.GONE
            }
        }

        binding.floatingActionButtonDelete.setOnClickListener {
            adapter.collection = emptyList()
        }

        return root
    }

    override fun onResume() {
        super.onResume()
        postViewModel.getPosts()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecyclerView() {
        adapter = PostAdapter()
        adapter.clickListener = { postResponse ->
            startActivity(
                Intent(context, DetailPostActivity::class.java).apply
                {
                    putExtra(POST_ID, postResponse)
                }
            )
        }
        binding.recyclerViewPosts.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewPosts.adapter = adapter
        binding.swipeRefreshPosts.setOnRefreshListener {
            binding.swipeRefreshPosts.isRefreshing = false
            postViewModel.getPosts()
        }

    }
}