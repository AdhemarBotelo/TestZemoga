package com.example.mobiletestzemoga.ui.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobiletestzemoga.data.network.PostResponse
import com.example.mobiletestzemoga.data.repository.PostRepository
import kotlinx.coroutines.launch

class PostViewModel(private val repository: PostRepository) : ViewModel() {

    private val _posts: MutableLiveData<List<PostResponse>> = MutableLiveData()
    val posts: LiveData<List<PostResponse>> = _posts

    private val _errorMessage: MutableLiveData<String> = MutableLiveData()
    val errorMessage: LiveData<String> = _errorMessage

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> = _loading

    fun getPosts() {
        viewModelScope.launch {
            try {
                _loading.value = true
                val favoritePost = repository.getAllFavoritePosts().toMutableList()
                val postResponse: List<PostResponse> = repository.getAllPost().filter {
                    !favoritePost.contains(it)
                }

                favoritePost.addAll(postResponse)
                _posts.value = favoritePost
            } catch (e: Exception) {
                _errorMessage.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }


}