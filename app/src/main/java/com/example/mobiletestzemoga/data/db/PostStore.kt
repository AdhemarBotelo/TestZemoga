package com.example.mobiletestzemoga.data.db

interface PostStore {
    suspend fun insertPost(post: Post)
    suspend fun insertComment(comment: Comment)
    suspend fun deletePosts(idPost: Long)
    suspend fun deleteComments()

    suspend fun getAllPost(): List<Post>
    suspend fun getPostWithComment(idPost:Long): PostWithComments
}

class PostStoreImpl(
    database: PostDatabase
) : PostStore {

    private val postDao = database.getPostDao()
    private val commentDao = database.getCommentDao()

    override suspend fun insertPost(post: Post) {
        postDao.insertPost(post)
    }

    override suspend fun insertComment(comment: Comment) {
        commentDao.insertComment(comment)
    }

    override suspend fun deletePosts(idPost: Long) {
        postDao.deletePosts(idPost)
    }

    override suspend fun deleteComments() {
        commentDao.deleteComments()
    }

    override suspend fun getAllPost(): List<Post> =
        postDao.getPosts()

    override suspend fun getPostWithComment(idPost:Long): PostWithComments =
        postDao.getPostsWithComments(idPost)
}