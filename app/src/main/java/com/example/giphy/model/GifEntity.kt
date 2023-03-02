package com.example.giphy.model

import java.io.Serializable

data class GifEntity(
    val type: String,
    val id: String,
    val title: String,
    val rating: String,
    val url: String,
) : Serializable
