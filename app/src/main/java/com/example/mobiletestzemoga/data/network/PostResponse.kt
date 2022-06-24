package com.example.mobiletestzemoga.data.network

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class PostResponse(
    @SerializedName("userId") var userId: Int,
    @SerializedName("id") var idPost: Long,
    @SerializedName("title") var title: String,
    @SerializedName("body") var body: String,
    var isFavorite: Boolean,
) : Serializable

data class CommentResponse(
    @SerializedName("postId") var postId: Long,
    @SerializedName("id") var idComment: Long,
    @SerializedName("name") var name: String,
    @SerializedName("email") var email: String,
    @SerializedName("body") var body: String,
)

