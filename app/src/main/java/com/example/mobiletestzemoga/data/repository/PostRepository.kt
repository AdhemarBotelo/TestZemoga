package com.example.mobiletestzemoga.data.repository

import com.example.mobiletestzemoga.data.db.Comment
import com.example.mobiletestzemoga.data.db.Post
import com.example.mobiletestzemoga.data.db.PostStore
import com.example.mobiletestzemoga.data.network.APIPostService
import com.example.mobiletestzemoga.data.network.CommentResponse
import com.example.mobiletestzemoga.data.network.NetworkHandler
import com.example.mobiletestzemoga.data.network.PostResponse

interface PostRepository {
    suspend fun getAllPost():List<PostResponse>
    suspend fun getAllFavoritePosts():List<PostResponse>
    suspend fun getAllPostComments(idPost:Long):List<CommentResponse>
    suspend fun addFavoritePost(postResponse: PostResponse)
    suspend fun deleteFavoritePost(idPost: Long)
}

class PostRepositoryImpl(
    private val networkHandler: NetworkHandler,
    private val service: APIPostService,
    private val postStore: PostStore,
) : PostRepository{

    override suspend fun getAllPost(): List<PostResponse> {
        return when(networkHandler.isNetworkAvailable()){
            true -> {
                val call  = service.getAllPosts()
                if (call.isSuccessful && call.body() != null){
                    call.body()!!
                }else {
                    emptyList()
                }
            }
            false -> postStore.getAllPost().map {
                convertPost(it)
            }
        }
    }

    override suspend fun getAllFavoritePosts(): List<PostResponse> {
        return postStore.getAllPost().map {
            convertPost(it)
        }
    }


    override suspend fun getAllPostComments(idPost: Long): List<CommentResponse> {
        return when(networkHandler.isNetworkAvailable()){
            true -> {
                postStore.deleteComments()
                val result = service.getPostComments(idPost).body() ?: emptyList()
                result.forEach {
                    postStore.insertComment(convertCommentResponse(it))
                }
                result
            }
            false -> {
                val resultStore = mutableListOf<CommentResponse>()
                postStore.getPostWithComment(idPost).comments.forEach {
                    resultStore.add(convertComment(it))
                }
                resultStore
            }
        }
    }

    override suspend fun addFavoritePost(postResponse: PostResponse) {
        postResponse.isFavorite = true
        postStore.insertPost(convertPostResponse(postResponse))
    }

    override suspend fun deleteFavoritePost(idPost: Long) {
        postStore.deletePosts(idPost)
    }


    private fun convertPostResponse(postResponse:PostResponse): Post{
        return Post(
            postResponse.idPost,
            postResponse.userId,
            postResponse.title,
            postResponse.body,
            postResponse.isFavorite
        )
    }

    private fun convertPost(post:Post): PostResponse{
        return PostResponse(
            post.userId,
            post.id,
            post.title,
            post.body,
            post.isFavorite,
        )
    }

    private fun convertCommentResponse(commentResponse: CommentResponse):Comment{
        return Comment(
            commentResponse.idComment,
            commentResponse.name,
            commentResponse.email,
            commentResponse.body,
            commentResponse.postId,
        )
    }

    private fun convertComment(comment:Comment):CommentResponse{
        return CommentResponse(
            comment.postId,
            comment.id,
            comment.name,
            comment.email,
            comment.body,
        )
    }
}