package com.example.mobiletestzemoga.ui.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobiletestzemoga.R
import com.example.mobiletestzemoga.core.POST_ID
import com.example.mobiletestzemoga.data.network.PostResponse
import com.example.mobiletestzemoga.databinding.ActivityDetailPostBinding
import com.example.mobiletestzemoga.ui.CommentAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailPostActivity : AppCompatActivity() {

    private var _binding: ActivityDetailPostBinding? = null
    private lateinit var adapter: CommentAdapter
    private val detailPostViewModel: DetailPostViewModel by viewModel()
    private var postResponse: PostResponse? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        postResponse = intent.getSerializableExtra(POST_ID) as? PostResponse

        initRecyclerView()

        postResponse?.let { post ->
            binding.textTitle.text = post.title
            binding.textDescription.text = post.body
            detailPostViewModel.getComments(post.idPost)
        }

        detailPostViewModel.comments.observe(this) {
            adapter.collection = it
        }

        detailPostViewModel.errorMessage.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

        detailPostViewModel.wasAdded.observe(this){
            Toast.makeText(this, "post ${it} was added to favorite list", Toast.LENGTH_SHORT).show()
            onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.detail_post_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_addFavorite) {
            postResponse?.let {
                detailPostViewModel.addFavorite(it)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initRecyclerView() {
        adapter = CommentAdapter()
        binding.recyclerViewComments.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewComments.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}