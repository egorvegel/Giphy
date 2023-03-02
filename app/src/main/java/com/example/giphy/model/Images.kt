package com.example.giphy.model

data class Images(
    val fixed_height: FixedHeight,
    val fixed_height_downsampled: FixedHeightDownsampled,
    val fixed_height_small: FixedHeightSmall,
    val fixed_width: FixedWidth,
    val fixed_width_downsampled: FixedWidthDownsampled,
    val fixed_width_small: FixedWidthSmall,
    val original: Original
)