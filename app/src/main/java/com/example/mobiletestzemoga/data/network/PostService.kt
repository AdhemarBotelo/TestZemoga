package com.example.mobiletestzemoga.data.network

import retrofit2.Response
import retrofit2.Retrofit

class PostService (retrofit: Retrofit) :APIPostService {
    private val postsApi by lazy { retrofit.create(APIPostService::class.java) }

    override suspend fun getAllPosts(): Response<List<PostResponse>> =
        postsApi.getAllPosts()


    override suspend fun getPostComments(idPost: Long): Response<List<CommentResponse>> =
        postsApi.getPostComments(idPost)

}