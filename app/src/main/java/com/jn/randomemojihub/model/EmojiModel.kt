package com.jn.randomemojihub.model

import com.google.gson.annotations.SerializedName

/**
 * File Description: Emoji data model
 *
 * Created on 15-06-2024.
 */
data class EmojiModel(
    @field:SerializedName("htmlCode")
    val htmlCode: List<String?>? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("unicode")
    val unicode: List<String?>? = null,

    @field:SerializedName("category")
    val category: String? = null,

    @field:SerializedName("group")
    val group: String? = null
)