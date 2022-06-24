package com.example.mobiletestzemoga.data.db

import androidx.room.*

@Dao
interface PostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(vararg post: Post)

    @Query("Delete From posts Where id=:idPost")
    suspend fun deletePosts(idPost:Long)

    @Query("Select * from posts")
    suspend fun getPosts():List<Post>

    @Transaction
    @Query("SELECT * FROM Posts WHERE id=:idPost")
    suspend fun getPostsWithComments(idPost:Long): PostWithComments
}