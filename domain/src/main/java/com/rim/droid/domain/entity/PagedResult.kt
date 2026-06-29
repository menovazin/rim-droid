package com.rim.droid.domain.entity

/**
 * Page of domain items together with pagination metadata coming from the API `info` block.
 */
data class PagedResult<T>(
    val items: List<T>,
    val totalPages: Int,
    val nextPage: Int?,
)
