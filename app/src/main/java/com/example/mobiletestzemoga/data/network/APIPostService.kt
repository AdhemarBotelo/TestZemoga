package com.example.mobiletestzemoga.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface APIPostService {
    companion object {
        const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    }
    @GET("posts/")
    suspend fun getAllPosts(): Response<List<PostResponse>>

    @GET("posts/{idPost}/comments")
    suspend fun getPostComments(@Path("idPost") idPost:Long): Response<List<CommentResponse>>
}