package com.etasdemir.ethinspector.data.cache

import com.etasdemir.ethinspector.data.ResponseResult

/**
 * Response = T, Entity = K, Domain = V
 * */
abstract class CacheStrategy<T, K, V>(
    private val responseToDomain: (ResponseResult<T>) -> ResponseResult<V>,
    private val localToDomain: (K) -> V,
    private val domainToLocal: (V) -> K,
) {
    abstract suspend fun execute(
        fetchFromService: suspend () -> ResponseResult<T>,
        fetchFromLocal: suspend () -> K?,
        persistResponse: suspend (K) -> Unit
    ): ResponseResult<V>
}