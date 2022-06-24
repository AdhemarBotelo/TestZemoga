package com.example.mobiletestzemoga.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobiletestzemoga.data.network.CommentResponse
import com.example.mobiletestzemoga.data.network.PostResponse
import com.example.mobiletestzemoga.data.repository.PostRepository
import kotlinx.coroutines.launch

class DetailPostViewModel(private val repository: PostRepository) : ViewModel() {
    private val _comments: MutableLiveData<List<CommentResponse>> = MutableLiveData()
    val comments: LiveData<List<CommentResponse>> = _comments

    private val _errorMessage: MutableLiveData<String> = MutableLiveData()
    val errorMessage: LiveData<String> = _errorMessage

    private val _wasAdded: MutableLiveData<Long> = MutableLiveData()
    val wasAdded: LiveData<Long> = _wasAdded

    fun getComments(idPost: Long) {
        viewModelScope.launch {
            try {
                _comments.value = repository.getAllPostComments(idPost)
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }

    fun addFavorite(postResponse: PostResponse) {
        viewModelScope.launch {
            try {
                postResponse.isFavorite = true
                repository.addFavoritePost(postResponse)
                _wasAdded.value = postResponse.idPost
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }
}