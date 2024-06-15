package com.jn.randomemojihub.api

import com.jn.randomemojihub.model.EmojiModel
import retrofit2.Response
import retrofit2.http.GET

/**
 * Created on 15-06-2024.
 */
interface EmojiApiService {

    @GET(ApiConfig.RANDOM_ENDPOINT)
    suspend fun getRandomEmoji(): Response<EmojiModel>
}