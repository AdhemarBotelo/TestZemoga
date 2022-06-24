package com.example.mobiletestzemoga.ui.post

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mobiletestzemoga.data.network.PostResponse
import com.example.mobiletestzemoga.data.repository.PostRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class PostViewModelTest {

    @RelaxedMockK
    @MockK
    private lateinit var postRepository: PostRepository
    private lateinit var postViewModel: PostViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()


    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        postViewModel = PostViewModel(postRepository)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getPost SHOULD return null WHEN there is an error`() = runTest {
        // GIVEN
        coEvery { postRepository.getAllPost() } throws NullPointerException()

        // WHEN
        postViewModel.getPosts()

        // THEN
        assertEquals(null, postViewModel.posts.value)
    }

    @Test
    fun `getPost SHOULD return a list with favorites and api response WHEN there is no error`() = runTest {
        // GIVEN
        coEvery { postRepository.getAllPost() } returns postResponse
        coEvery { postRepository.getAllFavoritePosts() } returns postFavoriteResponse

        // WHEN
        postViewModel.getPosts()

        // THEN
        assertEquals(postFavoriteResponse.size + postResponse.size, postViewModel.posts.value?.size)
    }

    @Test
    fun `getPost SHOULD return a error message WHEN there is an error`() = runTest {
        // GIVEN
        val errorMessage = "error message"
        coEvery { postRepository.getAllFavoritePosts() } returns postFavoriteResponse
        coEvery { postRepository.getAllPost() } throws NullPointerException(errorMessage)

        // WHEN
        postViewModel.getPosts()

        // THEN
        assertEquals(errorMessage, postViewModel.errorMessage.value)
    }

    companion object {
        var postResponse = listOf(
            PostResponse(1, 1, "title1", "body1", false),
            PostResponse(2, 2, "title2", "body2", false),
        )

        val postFavoriteResponse = listOf(
            PostResponse(3, 3, "title3", "body3", true),
        )
    }
}