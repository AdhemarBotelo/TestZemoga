package com.example.mobiletestzemoga.data.db

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "Posts")
data class Post(
    @PrimaryKey val id: Long = 0,
    val userId: Int = 0,
    val title: String,
    val body: String,
    val isFavorite:Boolean,
)

@Entity(tableName = "Comments")
data class Comment(
    @PrimaryKey val id: Long = 0,
    val name:String,
    val email:String,
    val body:String,
    val postId:Long,

)

data class PostWithComments(
    @Embedded val post: Post,
    @Relation(
        parentColumn = "id",
        entityColumn = "postId"
    )
    val comments: List<Comment>
)