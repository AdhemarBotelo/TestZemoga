package com.example.mobiletestzemoga.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mobiletestzemoga.data.network.CommentResponse
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
class DetailPostViewModelTest{
    @RelaxedMockK
    @MockK
    private lateinit var postRepository: PostRepository
    private lateinit var detailPostViewModel: DetailPostViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        detailPostViewModel = DetailPostViewModel(postRepository)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getComments SHOULD return null WHEN there is an error`() = runTest {
        // GIVEN
        coEvery { postRepository.getAllPostComments(idPost) } throws NullPointerException()

        // WHEN
        detailPostViewModel.getComments(idPost)

        // THEN
        assertEquals(null, detailPostViewModel.comments.value)
    }

    @Test
    fun `getComments SHOULD return a list with comments WHEN there is no error`() = runTest {
        // GIVEN
        coEvery { postRepository.getAllPostComments(idPost) } returns comments

        // WHEN
        detailPostViewModel.getComments(idPost)

        // THEN
        assertEquals(comments, detailPostViewModel.comments.value)
    }

    @Test
    fun `addFavorite SHOULD add post to favorite and return id post` () = runTest{
        // GIVEN
        coEvery { postRepository.addFavoritePost(postResponse) } returns Unit

        // WHEN
        detailPostViewModel.addFavorite(postResponse)

        // THEN
        assertEquals(postResponse.idPost, detailPostViewModel.wasAdded.value)
    }

    companion object{
        val comments = listOf(
            CommentResponse(1,1,"comment","email","body"),
            CommentResponse(1,2,"nanme","email","body")
        )

        var postResponse =
            PostResponse(1, 1, "title1", "body1", false)
        const val idPost:Long = 1
    }
}