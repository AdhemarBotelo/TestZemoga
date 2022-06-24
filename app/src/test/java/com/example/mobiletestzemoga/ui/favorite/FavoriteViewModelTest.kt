package com.example.mobiletestzemoga.ui.favorite

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
class FavoriteViewModelTest {
    @RelaxedMockK
    @MockK
    private lateinit var postRepository: PostRepository
    private lateinit var favoriteViewModel: FavoriteViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()


    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        favoriteViewModel = FavoriteViewModel(postRepository)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getPost SHOULD return null WHEN there is an error`() = runTest {
        // GIVEN
        coEvery { postRepository.getAllFavoritePosts() } throws NullPointerException()

        // WHEN
        favoriteViewModel.getFavoritePosts()

        // THEN
        assertEquals(null, favoriteViewModel.posts.value)
    }

    @Test
    fun `getPost SHOULD return a list with favorites and api response WHEN there is no error`() =
        runTest {
            // GIVEN
            coEvery { postRepository.getAllFavoritePosts() } returns listPostFavoriteResponse

            // WHEN
            favoriteViewModel.getFavoritePosts()

            // THEN
            assertEquals(listPostFavoriteResponse, favoriteViewModel.posts.value)
        }

    @Test
    fun `getPost SHOULD return a error message WHEN there is an error`() = runTest {
        // GIVEN
        val errorMessage = "error message"
        coEvery { postRepository.getAllFavoritePosts() } throws NullPointerException(errorMessage)

        // WHEN
        favoriteViewModel.getFavoritePosts()

        // THEN
        assertEquals(errorMessage, favoriteViewModel.errorMessage.value)
    }

    @Test
    fun `removeFavorite SHOULD return true`() = runTest {
        //GIVEN
        coEvery { postRepository.deleteFavoritePost(idPost) } returns Unit

        //WHEN
        favoriteViewModel.removeFavorite(postResponse)

        //THEN
        assertEquals(true, favoriteViewModel.wasDeleted.value)

    }

    companion object {
        const val idPost: Long = 1
        val listPostFavoriteResponse = listOf(
            PostResponse(1, 1, "title3", "body3", true),
        )
        val postResponse = PostResponse(1 ,idPost,"title","body",true)

    }
}