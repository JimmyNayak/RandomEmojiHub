package com.jn.randomemojihub.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jn.randomemojihub.api.EmojiApiService
import com.jn.randomemojihub.model.EmojiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.ResponseBody.Companion
import org.junit.*
import org.mockito.*
import retrofit2.Response

/**
 * Created on 15-06-2024.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class RandomEmojiHubViewModelTest {

    @Mock
    private lateinit var emojiApiService: EmojiApiService

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun testGetRandomEmojiNullDataTest() = runTest {
        Mockito.`when`(emojiApiService.getRandomEmoji()).thenReturn(Response.success(EmojiModel()))
        val viewModel = RandomEmojiHubViewModel(emojiApiService)
        viewModel.getRandomEmoji()
        val result = viewModel.randomEmoji.value
        Assert.assertEquals(result, EmojiModel())

    }

    @Test
    fun testGetRandomEmojiFailedTest() = runTest {
        Mockito.`when`(emojiApiService.getRandomEmoji())
            .thenReturn(Response.error(404, Companion.create(null, "null")))
        val viewModel = RandomEmojiHubViewModel(emojiApiService)
        viewModel.getRandomEmoji()
        testDispatcher.scheduler.advanceUntilIdle()
        val result = viewModel.errorMessage.value
        Assert.assertTrue(result > 0)
    }

    @Test
    fun testGetRandomEmojiSuccessTest() = runTest {
        val emojiModel = getEmoji()
        Mockito.`when`(emojiApiService.getRandomEmoji()).thenReturn(Response.success(emojiModel))
        val viewModel = RandomEmojiHubViewModel(emojiApiService)
        viewModel.getRandomEmoji()
        testDispatcher.scheduler.advanceUntilIdle()
        val result = viewModel.randomEmoji.value
        Assert.assertEquals(result.name, emojiModel.name)
        Assert.assertEquals(result.category, emojiModel.category)
        Assert.assertEquals(result.group, emojiModel.group)
        Assert.assertEquals(result.unicode, emojiModel.unicode)
        Assert.assertEquals(result.htmlCode, emojiModel.htmlCode)
    }

    private fun getEmoji() =
        EmojiModel(
            htmlCode = arrayListOf("&#129303;"),
            name = "hugging face",
            unicode = arrayListOf("U+1F917"),
            group = "face positive",
            category = "smileys and people"
        )

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}