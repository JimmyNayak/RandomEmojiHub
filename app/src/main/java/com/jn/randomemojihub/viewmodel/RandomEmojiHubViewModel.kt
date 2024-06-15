package com.jn.randomemojihub.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jn.randomemojihub.R
import com.jn.randomemojihub.api.EmojiApiService
import com.jn.randomemojihub.model.EmojiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * File Description: Random Emoji hub view model for tasks execution
 *
 * Created on 15-06-2024.
 */
@HiltViewModel
class RandomEmojiHubViewModel @Inject constructor(private val emojiApiService: EmojiApiService) : ViewModel() {

    var randomEmoji = MutableStateFlow(EmojiModel())
    var isLoading = MutableStateFlow(false)
    var errorMessage = MutableStateFlow(0)

    //  Get random emoji from server
    fun getRandomEmoji() {
        viewModelScope.launch {
            try {
                isLoading.value = true
                val response = emojiApiService.getRandomEmoji()
                if (response.isSuccessful) {
                    randomEmoji.value = response.body() ?: EmojiModel()
                } else {
                    errorMessage.value = R.string.error_something_went_wrong
                }
                isLoading.value = false
            } catch (e: Exception) {
                Log.e("Error:", e.message.toString())
                errorMessage.value = R.string.error_something_went_wrong
            } finally {
                isLoading.value = false
            }
        }
    }
}