package com.example.mobiletestzemoga.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobiletestzemoga.data.network.PostResponse
import com.example.mobiletestzemoga.data.repository.PostRepository
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: PostRepository) : ViewModel() {

    private val _posts: MutableLiveData<List<PostResponse>> = MutableLiveData()
    val posts: LiveData<List<PostResponse>> = _posts

    private val _errorMessage: MutableLiveData<String> = MutableLiveData()
    val errorMessage: LiveData<String> = _errorMessage

    private val _wasDeleted: MutableLiveData<Boolean> = MutableLiveData()
    val wasDeleted: LiveData<Boolean> = _wasDeleted

    fun removeFavorite(postResponse: PostResponse) {
        viewModelScope.launch {
            try {
                repository.deleteFavoritePost(postResponse.idPost)
                _wasDeleted.value = true
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }

    fun getFavoritePosts() {
        viewModelScope.launch {
            try {
                val favoritePost = repository.getAllFavoritePosts().toMutableList()
                _posts.value = favoritePost
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }
}