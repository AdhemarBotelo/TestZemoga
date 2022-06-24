package com.example.mobiletestzemoga.data.db

import androidx.room.*

@Dao
interface CommentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComment(vararg comment: Comment)

    @Query("Delete From comments")
    suspend fun deleteComments()

    @Query("Select * From comments Where postId=:postId")
    suspend fun getComments(postId:Long):List<Comment>
}