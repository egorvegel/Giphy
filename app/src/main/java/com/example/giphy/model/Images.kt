package com.example.giphy.model

data class Images(
    val fixed_height: FixedHeight,
    val fixed_width: FixedWidth,
)

data class FixedHeight(
    val height: String,
    val mp4: String,
    val mp4_size: String,
    val size: String,
    val url: String,
    val webp: String,
    val webp_size: String,
    val width: String
)

data class FixedWidth(
    val height: String,
    val mp4: String,
    val mp4_size: String,
    val size: String,
    val url: String,
    val webp: String,
    val webp_size: String,
    val width: String
)