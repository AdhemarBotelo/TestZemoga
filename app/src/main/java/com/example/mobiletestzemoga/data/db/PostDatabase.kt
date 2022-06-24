package com.example.mobiletestzemoga.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Post::class, Comment::class], version = 1)
abstract class PostDatabase : RoomDatabase() {
    abstract fun getPostDao(): PostDao
    abstract fun getCommentDao(): CommentDao
}